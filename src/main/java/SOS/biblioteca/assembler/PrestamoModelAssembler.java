package SOS.biblioteca.assembler;


import SOS.biblioteca.controller.UsuarioController;
import SOS.biblioteca.model.Prestamo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PrestamoModelAssembler extends RepresentationModelAssemblerSupport<Prestamo,Prestamo> {
    public PrestamoModelAssembler() {
        super(UsuarioController.class, Prestamo.class);
    }

    @Override
    public Prestamo toModel(Prestamo entity) {

        entity.add(linkTo(methodOn(UsuarioController.class).getPrestamo(entity.getId())).withSelfRel());
        return entity;
    }
}