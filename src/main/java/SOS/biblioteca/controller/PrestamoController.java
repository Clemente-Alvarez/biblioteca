package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SOS.biblioteca.controller.*;

import SOS.biblioteca.exceptions.PrestamoExistsException;
import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.service.PrestamoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/prestamos")
@XmlRootElement
@AllArgsConstructor
public class PrestamoController {

    private final PrestamoService service;

    @PostMapping()
    public ResponseEntity<Void> nuevoPrestamo(@Valid @RequestBody Prestamo newPrestamo){
        if(!service.exists(newPrestamo.getPrestamoId())){
            Prestamo prestamo = service.create(newPrestamo);

            return ResponseEntity.created(linkTo(PrestamoController.class).slash(prestamo.getPrestamoId()).toUri()).build();
        }
        throw new PrestamoExistsException(newPrestamo.getPrestamoId());
    }

    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml"})
    public ResponseEntity<Prestamo> getPrestamo(@PathVariable Integer id) {
        Prestamo prestamo = service.search(id)
                .orElseThrow(() -> new PrestamoExistsException(id));
                prestamo.add(linkTo(methodOn(PrestamoController.class).getPrestamo(id)).withSelfRel());
        return ResponseEntity.ok(prestamo);
    }
    
}

