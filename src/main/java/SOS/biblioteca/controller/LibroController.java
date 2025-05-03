package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SOS.biblioteca.model.Ejemplar;

import SOS.biblioteca.exceptions.LibroExistsException;
import SOS.biblioteca.model.Libro;
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
            Libro libro = service.create(newLibro);

            return ResponseEntity.created(linkTo(LibroController.class).slash(libro.getId()).toUri()).build();
        }
        throw new LibroExistsException(newLibro.getId());
    }

    @GetMapping(value = "", produces = { "application/json", "application/xml" })
    public ResponseEntity<PagedModel<Libro>> getLibros(
            @RequestParam(defaultValue = "", required = false) String titulo,
            @RequestParam(defaultValue = "", required = false) String estado,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        Page<Libros> libros = service.buscarLibros(titulo,estado,page,size);
         // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(libros, libroModelAssembler));
    }

    @GetMapping(value = "/{id}/ejemplares", produces = { "application/json", "application/xml" })
        public ResponseEntity<Libro> getLibroEjemplares(@PathVariable Integer id) {

                Libro libro = service.buscarLibroPorId(id)
                                .orElseThrow(() -> new LibroNotFoundException(id));
                Set<EntityModel<Ejemplares>> listaEjemplares = new HashSet<>();
                for (Ejemplar ejemplar : ejemplarService.buscarEjemplaresPorLibroId(id)) {
                        listaEjemplares.add(EntityModel.of(ejemplar,
                                        linkTo(methodOn(EjemplarController.class)
                                                        .getEjemplar(ejemplar.getId()))
                                                        .withSelfRel()));
                }
                libro.setListEjemplares(listaEjemplares);
                libro.add(linkTo(methodOn(LibroController.class).getLibro(id)).withSelfRel());
                return ResponseEntity.ok(libro);
        }
    
    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
        public ResponseEntity<Libro> getLibro(@PathVariable Integer id) {

                Libro libro = service.buscarLibroPorId(id)
                                .orElseThrow(() -> new LibroNotFoundException(id));
                libro.add(linkTo(methodOn(LibroController.class).getLibro(id)).withSelfRel());
                return ResponseEntity.ok(libro);
        }

    @GetMapping(value = "/{libroId}/ejemplares/{ejemplarId}", produces = { "application/json", "application/xml", "application/hal+json" })
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer libroId, @PathVariable Integer ejemplarId) {
        Libro libro = service.buscarLibroPorId(libroId)
                .orElseThrow(() -> new LibroNotFoundException);
        Ejemplar ejemplar = service.buscarEjemplarPorId(ejemplarId)
                .orElseThrow(() -> new EjemplarNotFoundException(id));
        ejemplar.add(linkTo(methodOn(LibroController.class).getEjemplar(libroId,ejemplarId)).withSelfRel());
        return ResponseEntity.ok(ejemplar);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> replaceLibro(@Valid @RequestBody Libro newLibro, 
            @PathVariable Integer id){
        service.buscarPorId(id)
                .map(Libro -> {
                    Libro.setTitulo(newLibro.getTitulo());
                    Libro.setIsbn(newLibro.getIsbn());
                    Libro.setAutor(newLibro.getAutor());
                    Libro.setEdicion(newLibro.getEdicion());
                    Libro.setEditorial(newLibro.getEditorial());
                    return service.crearLibro(Libro);
                })
                .orElseThrow(() -> new EmpleadoNotFoundException(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Integer id){
        if (service.existeLibroPorId(id) && !service.buscarLibrosPorIdYEstado(id).isEmpty()) {
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
