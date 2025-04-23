package SOS.biblioteca.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.*;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    @Query(value = "SELECT * FROM prestamo WHERE matricula = ?1", nativeQuery = true)
    List<Prestamo> findByUser_id(@Param("matricula") Integer id);

    @Query(value = "SELECT * FROM prestamo WHERE matricula = ?1 AND devuelto = ?2", nativeQuery = true)
    List<Prestamo> findByUser_id(@Param("matricula") Integer matricula, @Param("devuelto") Integer devuelto);

    @Query(value = "SELECT * FROM prestamo " +
           "WHERE fecha_inicio <= ?2 " +
           "AND fecha_devolucion >= ?1", nativeQuery = true)
    List<Prestamo> findByInterval(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
