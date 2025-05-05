package SOS.biblioteca.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.*;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    @Query(value = "SELECT * FROM prestamo WHERE matricula = ?1", nativeQuery = true)
    List<Prestamo> findByUser_id(@Param("matricula") Integer id);

    @Query(value = "SELECT * FROM prestamo WHERE matricula = ?1 AND devuelto = ?2", nativeQuery = true)
    List<Prestamo> findByUser_id(@Param("matricula") Integer matricula, @Param("devuelto") Integer devuelto);


    // Devuelve una lista de prestamos activos dado un intervalo de fechas y una matricula
    @Query(value = "SELECT * FROM prestamo " +
           "WHERE fecha_inicio <= ?3 " +
           "AND fecha_devolucion >= ?2" + 
           "AND matricula = ?1 " + 
           "AND devuelto = false", nativeQuery = true)
    List<Prestamo> findByIntervalAndUser_id(@Param("matricula") Integer matricula, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Prestamo save(Prestamo prestamo);

    boolean existsById(int id);

    Optional<Prestamo> findById(int id);

}
