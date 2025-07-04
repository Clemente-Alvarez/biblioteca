package SOS.biblioteca.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SOS.biblioteca.model.Libro;
import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.PrestamoId;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    @Query(value = "SELECT * FROM prestamo " +
                   "WHERE matricula = ?1 AND devuelto = ?2", nativeQuery = true)
    Page<Prestamo> findByUsuarioIdAndDevuelto(Integer usuarioId, Boolean devuelto, Pageable pageable);

    @Query(value = "SELECT e.* FROM ejemplar e JOIN prestamo p " +
                   "ON e.ejemplar_id = p.ejemplar_id WHERE p.matricula = ?1 AND p.devuelto = ?2", nativeQuery = true)
    List<Libro> findByUsuarioIdAndDevuelto(Integer usuarioId, Boolean devuelto);

    @Query(value = "SELECT * FROM prestamo " +
                   "WHERE matricula = ?1 AND devuelto = ?2 AND fecha_prestamo = ?3", nativeQuery = true)
    Page<Prestamo> findByUsuarioIdAndDevueltoAndFecha(Integer usuarioId, Boolean devuelto, LocalDate fecha, Pageable pageable);

    @Query(value = "SELECT * FROM prestamo p " +
                   "WHERE p.matricula = ?1 AND p.libro_id = ?2 AND p.fecha_prestamo = ?3", nativeQuery = true)
    Optional<Prestamo> findByUsuarioIdAndLibroIdAndFechaPrestamo(Integer usuarioId, Integer ejemplarId, LocalDate fecha);

}