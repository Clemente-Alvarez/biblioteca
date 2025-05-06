package SOS.biblioteca.controller;
import SOS.biblioteca.model.*;
import SOS.biblioteca.service.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import SOS.biblioteca.assembler.UsuarioModelAssembler;
import SOS.biblioteca.assembler.EjemplarModelAssembler;
import SOS.biblioteca.exceptions.*;
import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.Usuario;
import SOS.biblioteca.service.EjemplarService;
import SOS.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@RestController
@RequestMapping("/usuarios")
@XmlRootElement
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final EjemplarService ejemplarService;
    private final PrestamoService prestamoService;
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;
    private PagedResourcesAssembler<Ejemplar> pagedResourcesAssemblerEjemplar;
    private UsuarioModelAssembler usuarioModelAssembler;
    private EjemplarModelAssembler ejemplarModelAssembler;

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
        Usuario usuario = service.buscarUsuarioPorMatricula(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        usuario.add(linkTo(methodOn(UsuarioController.class).getUsuario(id)).withSelfRel());
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> replaceUsuario(@Valid @RequestBody Usuario newUsuario, @PathVariable Integer id) {
        service.buscarUsuarioPorMatricula(id)
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

    /*@PostMapping(value = "/{id}/prestamos")
    public ResponseEntity<Void> addPrestamos(@PathVariable Integer id,
            @Valid @RequestBody Ejemplar ejemplarPrestado) {

        Usuario usuario = service.buscarUsuarioPorId(id)
                        .orElseThrow(() -> new UsuarioNotFoundException(id));
        
        Ejemplar ejemplar = ejemplarService.buscarEjemplarPorId(ejemplarPrestado.getId())
                        .orElseThrow(() -> new EjemplarNotFoundException(ejemplarPrestado.getId()));

        if(ejemplar.getEstado() != "disponible") throw new EjemplarNotAvailableException(ejemplarPrestado.getId());

        String fechaUltimoPrestamo = ejemplarPrestado.getFechaUltimoPrestamo();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(fechaUltimoPrestamo, formatter);

        String penalizacion = usuario.getPenalizacion();
        LocalDate fechaPenalizacion = LocalDate.parse(penalizacion, formatter);
        if(fecha.isBefore(fechaPenalizacion)) throw new UserWithPenaltyException(id);
        
        LocalDate fechaUltimaDevolucion = fecha.plusWeeks(2);
        String resultado = fechaUltimaDevolucion.format(formatter);
        ejemplar.setFechaUltimoPrestamo(ejemplarPrestado.getFechaUltimoPrestamo());
        ejemplar.setFechaUltimaDevolucion(resultado);
        ejemplar.setEstado("no disponible");
        ejemplar.setPrestatario(usuario);
        ejemplarService.crearEjemplar(ejemplar);

        return ResponseEntity.noContent().build();
    }*/

    @PostMapping("/{id}/prestamos")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> addPrestamo(@PathVariable Integer id,
                        @RequestBody PrestamoId nuevoPrestamo) {
                // Buscar garaje y empleado en la base de datos
                Usuario usuario = service.buscarUsuarioPorMatricula(id)
                                .orElseThrow(() -> new UsuarioNotFoundException(id));

                Ejemplar ejemplar = ejemplarService.buscarEjemplarPorId(nuevoPrestamo.getEjemplarId())
                                .orElseThrow(() -> new EjemplarNotFoundException(
                                                nuevoPrestamo.getEjemplarId()));
                
                if(ejemplar.getEstado() != "disponible") throw new EjemplarNotAvailableException(nuevoPrestamo.getEjemplarId());
                
                String fechaPrestamo = nuevoPrestamo.getFechaPrestamo();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(fechaPrestamo, formatter);

                String penalizacion = usuario.getPenalizacion();
                LocalDate fechaPenalizacion = LocalDate.parse(penalizacion, formatter);
                if(fecha.isBefore(fechaPenalizacion)) throw new UserWithPenaltyException(id);
                
                LocalDate fechaDevolucion = fecha.plusWeeks(2);
                String resultado = fechaDevolucion.format(formatter);
                nuevoPrestamo.setFechaDevolucion(resultado);
                ejemplar.setEstado("no disponible");
                ejemplarService.crearEjemplar(ejemplar);
                prestamoService.crearPrestamo(nuevoPrestamo, usuario, ejemplar);

                return ResponseEntity.noContent().build();
        }

    /*@GetMapping(value = "/{id}/prestamos", produces = { "application/json", "application/xml" })
    public ResponseEntity<PagedModel<Ejemplar>> getPrestamos(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "", required = false) String fechaUltimoPrestamo,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {
        
        Usuario usuario = service.buscarUsuarioPorId(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        Page<Ejemplar> ejemplares = ejemplarService.buscarPrestamos(usuario, fechaUltimoPrestamo, page, size);

        // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ejemplares, ejemplarModelAssembler));
    }*/
    
    // @GetMapping(value = "/{id}/prestamos", produces = { "application/json", "application/xml" })
    // public ResponseEntity<PagedModel<Ejemplar>> getPrestamos(
    //         @PathVariable Integer id,
    //         @RequestParam(defaultValue = "0", required = false) int page,
    //         @RequestParam(defaultValue = "2", required = false) int size,
    //         @RequestParam(defaultValue = "false") boolean devuelto,
    //         @RequestParam(defaultValue = "", required = false) String fecha) {

    //     Usuario usuario = service.buscarUsuarioPorMatricula(id)
    //             .orElseThrow(() -> new UsuarioNotFoundException(id));

    //     List<Prestamo> prestamos = prestamoService.buscarPorUsuarioId(id);
        
    //     return ResponseEntity.ok(pagedResourcesAssemblerEjemplar.toModel(ejemplares, ejemplarModelAssembler));
    // }


}
