package SOS.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.model.Usuario;
import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.PrestamoId;
import SOS.biblioteca.repository.PrestamoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrestamoService {

    private final PrestamoRepository repository;

    public Page<Ejemplar> buscarPrestamos(int id, boolean devuelto, String fecha, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if(fecha != null && !fecha.isEmpty() && !devuelto) 
            return repository.findByUsuarioIdAndDevueltoAndFecha(id, devuelto, fecha, pageable);
        else return repository.findByUsuarioIdAndDevuelto(id, devuelto, pageable);
    }

    public List<Ejemplar> buscarPrestamos(int id, boolean devuelto) {
        return repository.findByUsuarioIdAndDevuelto(id, devuelto);
    }

    public Optional<Prestamo> buscarPrestamo(int usuarioId, int ejemplarId) {
        return repository.findByUsuarioIdAndEjemplarId(usuarioId, ejemplarId);
    }

    public Prestamo crearPrestamo(PrestamoId prestamoId, Usuario usuario, Ejemplar ejemplar,
                                  String fechaPrestamo, String fechaDevolucion, boolean devuelto) {
        // Crear la clave primaria compuesta
        prestamoId.setUsuarioId(usuario.getMatricula());

        // Crear la relación
        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        if(fechaPrestamo.isEmpty()) prestamo.setFechaPrestamo(fechaPrestamo);
        if(fechaDevolucion.isEmpty()) prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setDevuelto(devuelto);

        return repository.save(prestamo);
    }

}

