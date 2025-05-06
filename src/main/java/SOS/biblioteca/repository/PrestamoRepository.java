package SOS.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.PrestamoId;

public interface PrestamoRepository extends JpaRepository<Prestamo, PrestamoId> {

    // Buscar todos los garajes donde trabaja un empleado específico
    @Query(value = "SELECT e.* FROM ejemplar e JOIN prestamo p " +
                   "ON e.ejemplar_id = p.ejemplar_id WHERE p.matricula = ?1 AND p.devuelto = ?2", nativeQuery = true)
    Page<Ejemplar> findByUsuarioIdAndDevuelto(int usuarioId, boolean devuelto, Pageable pageable);

    @Query(value = "SELECT e.* FROM ejemplar e JOIN prestamo p " +
                   "ON e.ejemplar_id = p.ejemplar_id WHERE p.matricula = ?1 AND p.devuelto = ?2", nativeQuery = true)
    List<Ejemplar> findByUsuarioIdAndDevuelto(int usuarioId, boolean devuelto);

    @Query(value = "SELECT e.* FROM ejemplar e JOIN prestamo p " +
                   "ON e.ejemplar_id = p.ejemplar_id WHERE p.matricula = ?1 AND p.devuelto = ?2 AND p.fecha_inicio = ?3", nativeQuery = true)
    Page<Ejemplar> findByUsuarioIdAndDevueltoAndFecha(int usuarioId, boolean devuelto, String fecha, Pageable pageable);

    @Query(value = "SELECT * FROM prestamo p " +
                   "WHERE p.matricula = ?1 AND p.ejemplar_id = ?2 AND p.devuelto = 'false'", nativeQuery = true)
    Optional<Prestamo> findByUsuarioIdAndEjemplarId(int usuarioId, int ejemplarId);

}