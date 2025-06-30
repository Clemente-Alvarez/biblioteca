package SOS.biblioteca.assembler;


import SOS.biblioteca.model.Usuario;
import SOS.biblioteca.controller.UsuarioController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario,Usuario> {
    public UsuarioModelAssembler() {
        super(UsuarioController.class, Usuario.class);
    }

    @Override
    public Usuario toModel(Usuario entity) {

        entity.add(linkTo(methodOn(UsuarioController.class).getUsuario(entity.getId())).withSelfRel());
        return entity;
    }
}
