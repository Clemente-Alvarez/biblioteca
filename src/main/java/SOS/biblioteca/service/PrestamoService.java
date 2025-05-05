package SOS.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Prestamo> buscarPorUsuarioId(int id) {
        return repository.findByUsuarioId(id);
    }

    public void crearPrestamo(PrestamoId prestamoId, Usuario usuario, Ejemplar ejemplar) {
        // Crear la clave primaria compuesta
        prestamoId.setUsuarioId(usuario.getMatricula());

        // Crear la relación
        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);

        // Guardar en la base de datos
        repository.save(prestamo);
    }

}

