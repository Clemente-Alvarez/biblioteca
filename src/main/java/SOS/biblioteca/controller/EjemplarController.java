package SOS.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import SOS.biblioteca.exceptions.EjemplarExistsException;
import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.service.EjemplarService;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/ejemplares")
@XmlRootElement
@AllArgsConstructor
public class EjemplarController {
	
    private final EjemplarService service;
    private PagedResourcesAssembler<Ejemplar> pagedResourcesAssembler;
    private EjemplarModelAssembler ejemplarModelAssembler;

    @PostMapping()
    public ResponseEntity<Void> nuevoEjemplar(@Valid @RequestBody Ejemplar newEjemplar){
        if(!service.exists(newEjemplar.getEjemplarId())){
            Ejemplar ejemplar = service.create(newEjemplar);

            return ResponseEntity.created(linkTo(EjemplarController.class).slash(ejemplar.getEjemplar_id()).toUri()).build();
        }
        throw new EjemplarExistsException(newEjemplar.getEjemplar_id());
    }

    @GetMapping(value = "", produces = { "application/json", "application/xml" })
    public ResponseEntity<PagedModel<Ejemplar>> getEjemplares(
            @RequestParam(defaultValue = "", required = false) String titulo,
            @RequestParam(defaultValue = "", required = false) String estado,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        Page<Ejemplares> ejemplares = service.buscarEjemplares(titulo,estado,page,size);
        

        // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ejemplares, ejemplarModelAssembler));
    }

    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/hal+json" })
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer id) {
        Ejemplar ejemplar = service.buscarEjemplarPorId(id)
                .orElseThrow(() -> new EjemplarNotFoundException(id));
        ejemplar.add(linkTo(methodOn(EjemplarController.class).getEjemplar(id)).withSelfRel());
        return ResponseEntity.ok(ejemplar);
    }



}