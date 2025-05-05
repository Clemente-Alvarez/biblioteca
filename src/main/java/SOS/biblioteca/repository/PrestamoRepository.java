package SOS.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.PrestamoId;

public interface PrestamoRepository extends JpaRepository<Prestamo, PrestamoId> {

    // Buscar todos los garajes donde trabaja un empleado específico
    // List<Prestamo> findByUsuarioMatricula(int usuarioId);

    // List<Prestamo> findByUsuarioMatriculaAndDevuelto(int usuarioId, boolean devuelto);

    // List<Prestamo> findByUsuarioMatriculaAndDevueltoAndFecha(int usuarioId);

}