package SOS.biblioteca.assembler;


import SOS.biblioteca.model.Libro;
import SOS.biblioteca.controller.LibroController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


public class LibroModelAssembler extends RepresentationModelAssemblerSupport<Libro,Libro> {
    public LibroModelAssembler() {
        super(LibroController.class, Libro.class);
    }

    @Override
    public Libro toModel(Libro entity) {

        entity.add(linkTo(methodOn(LibroController.class).getLibro(entity.getId())).withSelfRel());
        return entity;
    }
}
