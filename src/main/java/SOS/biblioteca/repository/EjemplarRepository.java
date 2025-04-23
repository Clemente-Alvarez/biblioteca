package SOS.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {
    
    @Query(value = "SELECT * FROM ejemplar WHERE libro_id = ?1", nativeQuery = true)
    List<Ejemplar> findByLibro_id(@Param("regex") Integer id);

    @Query(value = "SELECT * FROM ejemplar WHERE libro_id = ?1 AND estado = ?2", nativeQuery = true)
    List<Ejemplar> findByLibro_id(@Param("regex") Integer id, @Param("estado") String estado);

}
