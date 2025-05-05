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


import SOS.biblioteca.exceptions.*;
import SOS.biblioteca.model.Usuario;
import SOS.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@XmlRootElement
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping()
    public ResponseEntity<Void> nuevoUsuario(@Valid @RequestBody Usuario newUsuario){
        if(!service.existeUsuarioPorCorreo(newUsuario.getCorreo())){
            Usuario usuario = service.crearUsuario(newUsuario);

            return ResponseEntity.created(linkTo(UsuarioController.class).slash(usuario.getMatricula()).toUri()).build();
        }
        throw new UsuarioExistsException(newUsuario.getMatricula());
    }

    @GetMapping(value = "", produces = { "application/json", "application/xml" })
    public ResponseEntity<PagedModel<Usuario>> getUsuarios(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {

        Page<Usuario> usuarios = service.buscarUsuarios(page, size);

        // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(usuarios, usuarioModelAssembler));
    }

    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/hal+json" })
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        Usuario usuario = service.buscarUsuarioPorId(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        usuario.add(linkTo(methodOn(UsuarioController.class).getUsuario(id)).withSelfRel());
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> replaceUsuario(@Valid @RequestBody Usuario newUsuario, @PathVariable Integer id) {
        service.buscarUsuarioPorId(id)
                .map(Usuario -> {
                    Usuario.setNombre(newUsuario.getNombre());
                    Usuario.setFechaNacimiento(newUsuario.getFechaNacimiento());
                    Usuario.setCorreo(newUsuario.getCorreo());
                    Usuario.setPenalizacion(newUsuario.getPenalizacion());
                    return service.crearUsuario(Usuario);
                })
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        if (service.existeUsuarioPorId(id)) {
            service.eliminarUsuario(id);
        } else {
            throw new UsuarioNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    //@PostMapping(value = "/{id}/prestamos"); 
    

}
