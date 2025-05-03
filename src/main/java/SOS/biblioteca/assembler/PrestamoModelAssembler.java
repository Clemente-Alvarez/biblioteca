package SOS.biblioteca.assembler;


import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.controller.PrestamoController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


public class PrestamoModelAssembler extends RepresentationModelAssemblerSupport<Prestamo,Prestamo> {
    public PrestamoModelAssembler() {
        super(PrestamoController.class, Prestamo.class);
    }

    @Override
    public Prestamo toModel(Prestamo entity) {

        entity.add(linkTo(methodOn(PrestamoController.class).getPrestamo(entity.getPrestamoId())).withSelfRel());
        return entity;
    }
}
