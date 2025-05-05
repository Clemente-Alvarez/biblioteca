package SOS.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.PrestamoId;

public interface PrestamoRepository extends JpaRepository<Prestamo, PrestamoId> {

    // Buscar todos los garajes donde trabaja un empleado específico
    List<Prestamo> findByUsuarioId(int usuarioId);

    List<Prestamo> findByUsuarioIdAndDevuelto(int usuarioId, boolean devuelto);

    List<Prestamo> findByUsuarioIdAndDevueltoAndFecha(int usuarioId);

}