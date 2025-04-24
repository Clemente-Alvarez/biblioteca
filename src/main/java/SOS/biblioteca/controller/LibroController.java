package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping()
    public ResponseEntity<Void> nuevoLibro(@Valid @RequestBody Libro newLibro){
        if(!service.exists(newLibro.getTitulo())){
            Libro libro = service.create(newLibro);

            return ResponseEntity.created(linkTo(LibroController.class).slash(libro.getIsbn()).toUri()).build();
        }
        throw new LibroExistsException(newLibro.getTitulo());
    }
    
}
