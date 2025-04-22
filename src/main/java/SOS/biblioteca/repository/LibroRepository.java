package SOS.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
    @Query(value = "SELECT * FROM libros WHERE titulo = ?1", nativeQuery = true)
    Optional<Libro> findByTitle(@Param("titulo") String titulo);
}
