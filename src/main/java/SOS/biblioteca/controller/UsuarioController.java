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
import SOS.biblioteca.assembler.PrestamoModelAssembler;
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
    private final UsuarioService usuarioService;
    private final PrestamoService prestamoService;
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;
    private PagedResourcesAssembler<Prestamo> pagedResourcesAssemblerPrestamo;
    private UsuarioModelAssembler usuarioModelAssembler;
    private PrestamoModelAssembler prestamoModelAssembler;

    @PostMapping()
    public ResponseEntity<Void> nuevoUsuario(@Valid @RequestBody Usuario newUsuario){
        if(!service.existeUsuarioPorMatricula(newUsuario.getMatricula())){
            Usuario usuario = service.crearUsuario(newUsuario);

            return ResponseEntity.created(linkTo(UsuarioController.class).slash(usuario.getId()).toUri()).build();
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
        Usuario usuario = service.buscarUsuarioPorId(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        usuario.add(linkTo(methodOn(UsuarioController.class).getUsuario(id)).withSelfRel());
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> replaceUsuario(@Valid @RequestBody Usuario newUsuario, @PathVariable Integer id) {
        service.buscarUsuarioPorId(id)
                .map(Usuario -> {
                    Usuario.setMatricula(newUsuario.getMatricula());
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
            service.eliminarUsuarioPorId(id);
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
                Usuario usuario = service.buscarUsuarioPorId(id)
                                .orElseThrow(() -> new UsuarioNotFoundException(id));

                Libro libro = libroService.buscarLibroPorId(nuevoPrestamo.getLibroId())
                                .orElseThrow(() -> new LibroNotFoundException(
                                                nuevoPrestamo.getLibroId()));
                
                if(libro.getDisponibles() <= 0) throw new EjemplarNotAvailableException(nuevoPrestamo.getLibroId());
                
                LocalDate fecha = nuevoPrestamo.getFechaInicio();

                LocalDate fechaPenalizacion = usuario.getPenalizacion();
                if(fechaPenalizacion != null && fecha.isBefore(fechaPenalizacion)) throw new UserWithPenaltyException(id);
                
                LocalDate fechaDevolucion = fecha.plusWeeks(2);
                Prestamo prestamo = prestamoService.crearPrestamo(usuario, libro,
                        fecha, fechaDevolucion, false);
                libro.setDisponibles(libro.getDisponibles()-1);
                libroService.crearLibro(libro);
                return ResponseEntity.created(linkTo(methodOn(UsuarioController.class).getPrestamo(id,prestamo.getId())).toUri()).build();
        }
    
    @GetMapping(value = "/{id}/prestamos", produces = { "application/json" })
    public ResponseEntity<PagedModel<Prestamo>> getPrestamos(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size,
            @RequestParam(defaultValue = "false", required = false) Boolean devuelto,
            @RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate fecha) {

        Usuario usuario = service.buscarUsuarioPorId(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        Page<Prestamo> prestamos; 
        if(fecha!=null) prestamos=prestamoService.buscarPrestamosPorFecha(id, devuelto, fecha, page, size);
        else prestamos=prestamoService.buscarPrestamos(id, devuelto, page, size);

        Set<Integer> usuarios = new HashSet<>();
        Set<Integer> libros = new HashSet<>();
        for (Prestamo p: prestamos) {
            if (!usuarios.contains(p.getUsuario().getId())) {
                p.getUsuario().add(linkTo(methodOn(UsuarioController.class).getUsuario(p.getUsuario().getId())).withSelfRel());
                usuarios.add(p.getUsuario().getId());
            }
            if (!libros.contains(p.getLibro().getId())) {
                p.getLibro().add(linkTo(methodOn(LibroController.class).getLibro(p.getLibro().getId())).withSelfRel());
                libros.add(p.getLibro().getId());
            }
        }
        
        return ResponseEntity.ok(pagedResourcesAssemblerPrestamo.toModel(prestamos, prestamoModelAssembler));
    }

    @GetMapping(value = "/{usuarioId}/prestamos/{id}", produces = { "application/json" })
    public ResponseEntity<Prestamo> getPrestamo(
            @PathVariable Integer usuarioId,
            @PathVariable Integer id) {

        Prestamo prestamo = prestamoService.buscarPrestamo(id)
                .orElseThrow(() -> new PrestamoNotFoundException(id));
        prestamo.add(linkTo(methodOn(UsuarioController.class).getPrestamo(usuarioId,id)).withSelfRel());
        return ResponseEntity.ok(prestamo);
    }

    @PutMapping("/{usuarioId}/prestamos/{id}")
    public ResponseEntity<Void> returnPrestamo(@Valid @RequestBody PrestamoId prestamoId, 
            @PathVariable Integer usuarioId,
            @PathVariable Integer id) {
        Usuario usuario = service.buscarUsuarioPorId(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException(usuarioId));
        Libro libro = libroService.buscarLibroPorId(prestamoId.getLibroId())
                .orElseThrow(() -> new LibroNotFoundException(prestamoId.getLibroId()));
        Prestamo prestamo = prestamoService.buscarPrestamo(id)
                .orElseThrow(() -> new PrestamoNotFoundException(id));
        if(prestamoId.getUsuarioId() != prestamo.getUsuario().getId()) throw new PrestamoLibroIdException();
        if(prestamoId.getLibroId() != prestamo.getLibro().getId()) throw new PrestamoUsuarioIdException();
        if(!prestamoId.getFechaInicio().isEqual(prestamo.getFechaPrestamo())) throw new PrestamoFechaInicioException();
        if(prestamo.getDevuelto()) throw new PrestamoYaDevueltoException(id);
        if(prestamoId.getFechaDevolucion() != null && prestamoId.getFechaPedidoAmpliacion() != null) {
            throw new DevolucionAmpliacionSimultaneaException(id);
        }
        if(prestamoId.getFechaDevolucion() != null && prestamoId.getFechaAmpliacion() != null) {
            throw new DevolucionAmpliacionSimultaneaException(id);
        }
        if(prestamoId.getFechaPedidoAmpliacion() != null && prestamoId.getFechaAmpliacion() != null) {
            throw new PedidoAmpliacionSinFechaException(id);
        }
        LocalDate dateDevolucionPrestamo = prestamo.getFechaDevolucion();
        LocalDate datePedidoAmpliacion = prestamoId.getFechaPedidoAmpliacion();
        if(datePedidoAmpliacion != null){
            LocalDate dateAmpliacion = prestamoId.getFechaAmpliacion();
            if(dateDevolucionPrestamo.isBefore(datePedidoAmpliacion)) throw new PrestamoTimeLimitExceededException(id);
            LocalDate dateNuevaDevolucion = dateDevolucionPrestamo.plusWeeks(2);
            if(!dateAmpliacion.isEqual(dateNuevaDevolucion)) throw new PrestamoAmpliacionNot2WException();
            prestamo.setFechaDevolucion(dateNuevaDevolucion);
            prestamoService.crearPrestamo(prestamo);
        }else{
            LocalDate dateDevolucionUsuario = prestamoId.getFechaDevolucion();
            if(dateDevolucionPrestamo.isBefore(dateDevolucionUsuario)) {
                usuario.setPenalizacion(dateDevolucionUsuario.plusWeeks(1));
                usuarioService.crearUsuario(usuario);
            }
            libro.setDisponibles(libro.getDisponibles()+1);
            libroService.crearLibro(libro);
            prestamo.setDevuelto(true);
            prestamo.setFechaDevolucion(dateDevolucionUsuario);
            prestamoService.crearPrestamo(prestamo);
        }
        return ResponseEntity.noContent().build();
    }
 
    @GetMapping(value = "/{id}/actividad", produces = { "application/json" })
    public ResponseEntity<UsuarioActivity> getActividad(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {

        Usuario usuario = service.buscarUsuarioPorId(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        List<Prestamo> prestamosActuales = prestamoService.buscarPrestamosActuales(id);
        List<Prestamo> prestamosHistorial = prestamoService.buscarPrestamosDevueltos(id);

        Set<Integer> usuarios = new HashSet<>();
        Set<Integer> libros = new HashSet<>();
        for (Prestamo p: prestamosActuales) {
            if (!libros.contains(p.getLibro().getId())) {
                p.getLibro().add(linkTo(methodOn(LibroController.class).getLibro(p.getLibro().getId())).withSelfRel());
                libros.add(p.getLibro().getId());
            }
            if (!usuarios.contains(p.getUsuario().getId())) {
                p.getUsuario().add(linkTo(methodOn(UsuarioController.class).getUsuario(p.getUsuario().getId())).withSelfRel());
                usuarios.add(p.getUsuario().getId());
            }
        }
        for (Prestamo p: prestamosHistorial) {
            if (!libros.contains(p.getLibro().getId())) {
                p.getLibro().add(linkTo(methodOn(LibroController.class).getLibro(p.getLibro().getId())).withSelfRel());
                libros.add(p.getLibro().getId());
            }
            if (!usuarios.contains(p.getUsuario().getId())) {
                p.getUsuario().add(linkTo(methodOn(UsuarioController.class).getUsuario(p.getUsuario().getId())).withSelfRel());
                usuarios.add(p.getUsuario().getId());
            }
        }

        UsuarioActivity usuarioActivity = new UsuarioActivity();
        usuarioActivity.setId(id);
        usuarioActivity.setMatricula(usuario.getMatricula());
        usuarioActivity.setNombre(usuario.getNombre());
        usuarioActivity.setCorreo(usuario.getCorreo());
        usuarioActivity.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioActivity.setPenalizacion(usuario.getPenalizacion());
        usuarioActivity.setListaPrestamosActuales(prestamosActuales);
        usuarioActivity.setListaPrestamosDevueltos(prestamosHistorial);
        usuarioActivity.add(linkTo(methodOn(UsuarioController.class).getUsuario(id)).withSelfRel());
        return ResponseEntity.ok(usuarioActivity);
    }
                

}
