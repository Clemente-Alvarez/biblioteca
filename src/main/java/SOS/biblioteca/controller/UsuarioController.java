package SOS.biblioteca.controller;
import SOS.biblioteca.model.*;
import SOS.biblioteca.service.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import SOS.biblioteca.assembler.UsuarioModelAssembler;
import SOS.biblioteca.assembler.LibroModelAssembler;
import SOS.biblioteca.exceptions.*;
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
    private final LibroService libroService;
    private final PrestamoService prestamoService;
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;
    private PagedResourcesAssembler<Libro> pagedResourcesAssemblerEjemplar;
    private UsuarioModelAssembler usuarioModelAssembler;
    private LibroModelAssembler libroModelAssembler;

    @PostMapping()
    public ResponseEntity<Void> nuevoUsuario(@Valid @RequestBody Usuario newUsuario){
        if(!service.existeUsuarioPorCorreo(newUsuario.getCorreo())){
            Usuario usuario = service.crearUsuario(newUsuario);

            return ResponseEntity.created(linkTo(UsuarioController.class).slash(usuario.getMatricula()).toUri()).build();
        }
        throw new UsuarioExistsException(newUsuario.getCorreo());
    }

    @GetMapping(value = "", produces = { "application/json" })
    public ResponseEntity<PagedModel<Usuario>> getUsuarios(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size) {

        Page<Usuario> usuarios = service.buscarUsuarios(page, size);

        // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(usuarios, usuarioModelAssembler));
    }

    @GetMapping(value = "/{id}", produces = { "application/json" })
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
        if (service.existeUsuarioPorMatricula(id)) {
            service.eliminarUsuarioPorMatricula(id);
        } else {
            throw new UsuarioNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/prestamos")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> addPrestamo(@PathVariable Integer id,
                        @RequestBody PrestamoId nuevoPrestamo) {
                // Buscar garaje y empleado en la base de datos
                Usuario usuario = service.buscarUsuarioPorMatricula(id)
                                .orElseThrow(() -> new UsuarioNotFoundException(id));

                Libro libro = libroService.buscarLibroPorId(nuevoPrestamo.getLibroId())
                                .orElseThrow(() -> new LibroNotFoundException(
                                                nuevoPrestamo.getLibroId()));
                
                if(libro.getDisponibles() <= 0) throw new EjemplarNotAvailableException(nuevoPrestamo.getLibroId());
                
                LocalDate fecha = nuevoPrestamo.getFechaInicio();

                LocalDate fechaPenalizacion = usuario.getPenalizacion();
                if(fecha.isBefore(fechaPenalizacion)) throw new UserWithPenaltyException(id);
                
                LocalDate fechaDevolucionReal = nuevoPrestamo.getFechaFin();
                LocalDate fechaDevolucion = fecha.plusWeeks(2);
                prestamoService.crearPrestamo(usuario, libro,
                        fecha, fechaDevolucion, false);
                libro.setDisponibles(libro.getDisponibles()-1);
                libroService.crearLibro(libro);
                return ResponseEntity.noContent().build();
        }
    
    @GetMapping(value = "/{id}/prestamos", produces = { "application/json" })
    public ResponseEntity<PagedModel<Libro>> getPrestamos(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size,
            @RequestParam(defaultValue = "false") Boolean devuelto,
            @RequestParam(required = false) @DateTimeFormat(pattern="dd-mm-yyyy") LocalDate fecha) {

        Usuario usuario = service.buscarUsuarioPorMatricula(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        Page<Libro> libros; 
        if(fecha!=null) libros=prestamoService.buscarPrestamosPorFecha(id, devuelto, fecha, page, size);
        else libros=prestamoService.buscarPrestamos(id, devuelto, page, size);
        
        return ResponseEntity.ok(pagedResourcesAssemblerEjemplar.toModel(libros, libroModelAssembler));
    }

    @PutMapping("/{usuarioId}/prestamos/{prestamoId}")
    public ResponseEntity<Void> returnPrestamo(@Valid @RequestBody PrestamoId prestamoId, 
            @PathVariable Integer id,
            @RequestParam(defaultValue = "false", required = false) boolean ampliacion) {
        Usuario usuario = service.buscarUsuarioPorMatricula(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        Libro libro = libroService.buscarLibroPorId(prestamoId.getLibroId())
                .orElseThrow(() -> new LibroNotFoundException(id));
        Prestamo prestamo = prestamoService.buscarPrestamo(id, prestamoId.getLibroId(), prestamoId.getFechaInicio())
                .orElseThrow(() -> new PrestamoNotFoundException(id));
        LocalDate dateDevolucionPrestamo = prestamo.getFechaDevolucion();
        if(ampliacion){
            LocalDate datePedidoAmpliacion = prestamoId.getFechaDevolucion();
            if(dateDevolucionPrestamo.isBefore(datePedidoAmpliacion)) throw new PrestamoTimeLimitExceededException(id);
            LocalDate dateNuevaDevolucion = dateDevolucionPrestamo.plusWeeks(2);
            prestamoService.crearPrestamo(usuario, 
                libro,prestamo.getFechaPrestamo(),dateNuevaDevolucion,false);
        }else{
            LocalDate dateDevolucionUsuario = prestamoId.getFechaDevolucion();
            if(dateDevolucionPrestamo.isBefore(dateDevolucionUsuario)) throw new PrestamoTimeLimitExceededException(id);
            libro.setDisponibles(libro.getDisponibles()+1);
            libroService.crearLibro(libro);
        }
        return ResponseEntity.noContent().build();
    }

    /* 
    @GetMapping(value = "/{id}/actividad", produces = { "application/json" })
    public ResponseEntity<Usuario> getActividad(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {

        Usuario usuario = service.buscarUsuarioPorMatricula(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        Set<EntityModel<Ejemplar>> listaPrestamosActuales = new HashSet<>();
                for (Ejemplar ejemplar : prestamoService.buscarPrestamos(id,false)) {
                        listaPrestamosActuales.add(EntityModel.of(ejemplar,
                                        linkTo(methodOn(EjemplarController.class)
                                                        .getEjemplar(ejemplar.getId()))
                                                        .withSelfRel()));
                }
        Set<EntityModel<Ejemplar>> listaPrestamosDevueltos = new HashSet<>();
                for (Ejemplar ejemplar : prestamoService.buscarPrestamos(id,true)) {
                        listaPrestamosDevueltos.add(EntityModel.of(ejemplar,
                                        linkTo(methodOn(EjemplarController.class)
                                                        .getEjemplar(ejemplar.getId()))
                                                        .withSelfRel()));
                }
                usuario.setListaPrestamosActuales(listaPrestamosActuales);
                usuario.setListaPrestamosDevueltos(listaPrestamosDevueltos);
                usuario.add(linkTo(methodOn(UsuarioController.class).getUsuario(id)).withSelfRel());
                return ResponseEntity.ok(usuario);
    }
                */

}
