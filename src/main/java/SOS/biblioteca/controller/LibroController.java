package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Set;
import java.util.HashSet;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;

import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.assembler.LibroModelAssembler;
import SOS.biblioteca.exceptions.DifferentIdException;
import SOS.biblioteca.exceptions.EjemplarNotFoundException;
import SOS.biblioteca.exceptions.LibroExistsException;
import SOS.biblioteca.exceptions.LibroNotFoundException;
import SOS.biblioteca.model.Libro;
import SOS.biblioteca.service.EjemplarService;
import SOS.biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/libros")
@XmlRootElement
@AllArgsConstructor
public class LibroController {

    private final LibroService service;
    private final EjemplarService ejemplarService;
    private PagedResourcesAssembler<Libro> pagedResourcesAssembler;
    private LibroModelAssembler libroModelAssembler;

    @PostMapping()
    public ResponseEntity<Void> nuevoLibro(@Valid @RequestBody Libro newLibro){
        if(!service.existeLibroPorIsbn(newLibro.getIsbn())){
            Libro libro = service.crearLibro(newLibro);

            return ResponseEntity.created(linkTo(LibroController.class).slash(libro.getId()).toUri()).build();
        }
        throw new LibroExistsException(newLibro.getId());
    }

    @GetMapping(value = "", produces = { "application/json", "application/xml" })
    public ResponseEntity<PagedModel<Libro>> getLibros(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size,
            @RequestParam(defaultValue = "", required = false) String titulo,
            @RequestParam(defaultValue = "", required = false) String estado) {
        Page<Libro> libros = service.buscarLibros(titulo,estado,page,size);
         // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(libros, libroModelAssembler));
    }

    /*@GetMapping(value = "/{id}/ejemplares", produces = { "application/json", "application/xml" })
        public ResponseEntity<Libro> getLibroEjemplares(@PathVariable Integer id,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {

                Libro libro = service.buscarLibroPorId(id)
                                .orElseThrow(() -> new LibroNotFoundException(id));
                Set<EntityModel<Ejemplar>> listaEjemplares = new HashSet<>();
                for (Ejemplar ejemplar : ejemplarService.buscarEjemplaresPorLibroId(id,page,size)) {
                        listaEjemplares.add(EntityModel.of(ejemplar,
                                        linkTo(methodOn(EjemplarController.class)
                                                        .getEjemplar(ejemplar.getId()))
                                                        .withSelfRel()));
                }
                libro.setListEjemplares(listaEjemplares);
                libro.add(linkTo(methodOn(LibroController.class).getLibro(id)).withSelfRel());
                return ResponseEntity.ok(libro);
        }
    */
    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
        public ResponseEntity<Libro> getLibro(@PathVariable Integer id) {

                Libro libro = service.buscarLibroPorId(id)
                                .orElseThrow(() -> new LibroNotFoundException(id));
                libro.add(linkTo(methodOn(LibroController.class).getLibro(id)).withSelfRel());
                return ResponseEntity.ok(libro);
        }

    /*@GetMapping(value = "/{libroId}/ejemplares/{ejemplarId}", produces = { "application/json", "application/xml", "application/hal+json" })
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer libroId, @PathVariable Integer ejemplarId) {
        Libro libro = service.buscarLibroPorId(libroId)
                .orElseThrow(() -> new LibroNotFoundException(libroId));
        Ejemplar ejemplar = service.buscarEjemplarPorId(ejemplarId)
                .orElseThrow(() -> new EjemplarNotFoundException(id));
        ejemplar.add(linkTo(methodOn(LibroController.class).getEjemplar(libroId,ejemplarId)).withSelfRel());
        return ResponseEntity.ok(ejemplar);
    }
    */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> replaceLibro(@Valid @RequestBody Libro newLibro, 
            @PathVariable Integer id){
        service.buscarLibroPorId(id)
                .map(Libro -> {
                    Libro.setTitulo(newLibro.getTitulo());
                    Libro.setIsbn(newLibro.getIsbn());
                    Libro.setAutor(newLibro.getAutor());
                    Libro.setEdicion(newLibro.getEdicion());
                    Libro.setEditorial(newLibro.getEditorial());
                    return service.crearLibro(Libro);
                })
                .orElseThrow(() -> new LibroNotFoundException(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Integer id,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size){
        if (service.existeLibroPorId(id) && !service.buscarLibrosPorIdYEstado(id,page,size).isEmpty()) {
            service.eliminarLibroPorId(id);
        } else {
            throw new LibroNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ejemplares")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> addEjemplarToLibro(@PathVariable Integer id,
                        @RequestBody Ejemplar ejemplar) {
                
                Libro libro = service.buscarLibroPorId(id)
                                .orElseThrow(() -> new LibroNotFoundException(id));
                if(id != ejemplar.getId()) throw new DifferentIdException(id,ejemplar.getId());
                ejemplar.setLibroId(id);
                ejemplarService.crearEjemplar(ejemplar);
                return ResponseEntity.noContent().build();
        }

}
