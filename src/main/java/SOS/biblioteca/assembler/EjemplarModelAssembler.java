package SOS.biblioteca.assembler;


import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.controller.EjemplarController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


public class EjemplarModelAssembler extends RepresentationModelAssemblerSupport<Ejemplar,Ejemplar> {
    public EjemplarModelAssembler() {
        super(EjemplarController.class, Ejemplar.class);
    }

    @Override
    public Ejemplar toModel(Ejemplar entity) {

        entity.add(linkTo(methodOn(EjemplarController.class).getEjemplar(entity.getId())).withSelfRel());
        return entity;
    }
}
