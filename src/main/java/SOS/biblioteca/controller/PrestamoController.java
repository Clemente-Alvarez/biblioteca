package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if(!service.exists(newPrestamo.getPrestamo_id())){
            Prestamo prestamo = service.create(newPrestamo);

            return ResponseEntity.created(linkTo(PrestamoController.class).slash(prestamo.getPrestamo_id()).toUri()).build();
        }
        throw new PrestamoExistsException(newPrestamo.getPrestamo_id());
    }
    
}

