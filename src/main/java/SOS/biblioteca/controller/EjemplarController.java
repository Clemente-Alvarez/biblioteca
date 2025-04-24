package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SOS.biblioteca.exceptions.EjemplarExistsException;
import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.service.EjemplarService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/ejemplares")
@XmlRootElement
@AllArgsConstructor
public class EjemplarController {

    private final EjemplarService service;

    @PostMapping()
    public ResponseEntity<Void> nuevoEjemplar(@Valid @RequestBody Ejemplar newEjemplar){
        if(!service.exists(newEjemplar.getEjemplar_id())){
            Ejemplar ejemplar = service.create(newEjemplar);

            return ResponseEntity.created(linkTo(EjemplarController.class).slash(ejemplar.getEjemplar_id()).toUri()).build();
        }
        throw new EjemplarExistsException(newEjemplar.getEjemplar_id());
    }
}