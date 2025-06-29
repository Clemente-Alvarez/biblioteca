package SOS.biblioteca.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import SOS.biblioteca.model.Usuario;
import SOS.biblioteca.model.Libro;
import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.PrestamoId;
import SOS.biblioteca.repository.PrestamoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrestamoService {

    private final PrestamoRepository repository;

    public Page<Libro> buscarPrestamosPorFecha(Integer id, Boolean devuelto, LocalDate fecha, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByUsuarioIdAndDevueltoAndFecha(id, devuelto, fecha, pageable);
    }

    public Page<Libro> buscarPrestamos(Integer id, Boolean devuelto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByUsuarioIdAndDevuelto(id, devuelto, pageable);
    }

    public List<Libro> buscarPrestamos(int id, boolean devuelto) {
        return repository.findByUsuarioIdAndDevuelto(id, devuelto);
    }

    public Optional<Prestamo> buscarPrestamo(Integer usuarioId, Integer libroId, LocalDate fecha) {
        return repository.findByUsuarioIdAndLibroIdAndFechaPrestamo(usuarioId, libroId, fecha);
    }

    public Prestamo crearPrestamo(Usuario usuario, Libro libro,
                                  LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto) {

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setDevuelto(devuelto);

        return repository.save(prestamo);
    }

}

