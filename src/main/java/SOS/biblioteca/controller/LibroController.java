package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;

import SOS.biblioteca.assembler.LibroModelAssembler;
import SOS.biblioteca.exceptions.*;
import SOS.biblioteca.model.Libro;
import SOS.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/libros")
@XmlRootElement
@AllArgsConstructor
public class LibroController {

    private final LibroService service;
    private PagedResourcesAssembler<Libro> pagedResourcesAssembler;
    private LibroModelAssembler libroModelAssembler;

    @PostMapping()
    public ResponseEntity<Void> nuevoLibro(@Valid @RequestBody Libro newLibro){
        if(newLibro.getId()!=null) throw new LibroIdNotNullException();
        if(service.existeLibroPorIsbn(newLibro.getIsbn())) throw new LibroExistsException(newLibro.getIsbn());
        newLibro.setDisponibles(newLibro.getEjemplares());
        Libro libro = service.crearLibro(newLibro);
        return ResponseEntity.created(linkTo(LibroController.class).slash(libro.getId()).toUri()).build();
    }

    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<PagedModel<Libro>> getLibros(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size,
            @RequestParam(defaultValue = "", required = false) String titulo,
            @RequestParam(defaultValue = "false", required = false) Boolean disponible) {
        Page<Libro> libros = service.buscarLibros(titulo,disponible,page,size);
         // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(libros, libroModelAssembler));
    }

    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Libro> getLibro(@PathVariable Integer id) {

        Libro libro = service.buscarLibroPorId(id)
                        .orElseThrow(() -> new LibroNotFoundException(id));
        libro.add(linkTo(methodOn(LibroController.class).getLibro(id)).withSelfRel());
        return ResponseEntity.ok(libro);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> replaceLibro(@Valid @RequestBody Libro newLibro, @PathVariable Integer id){
        Libro libro = service.buscarLibroPorId(id)
            .orElseThrow(() -> new LibroNotFoundException(id));
        if(libro.getEjemplares() - newLibro.getEjemplares() > libro.getDisponibles()) throw new DisponiblesInsuficientesException();

        newLibro.setId(id);
        newLibro.setDisponibles(libro.getDisponibles() - libro.getEjemplares() + newLibro.getEjemplares());
        service.crearLibro(newLibro);
        return ResponseEntity.noContent().build(); 
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Integer id,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size){
        Libro libro = service.buscarLibroPorId(id)
            .orElseThrow(() -> new LibroNotFoundException(id));
        if(libro.getDisponibles() != libro.getEjemplares()) throw new EjemplarPrestadoException(id);
       
        service.eliminarLibroPorId(id);
        return ResponseEntity.noContent().build();
    }

}
