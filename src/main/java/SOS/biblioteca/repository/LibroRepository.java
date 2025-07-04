package SOS.biblioteca.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import SOS.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
 
    
    //@Query(value = "SELECT * FROM libro WHERE titulo ~ :regex", nativeQuery = true)
    //List<Libro> findByTituloRegex(@Param("regex") String regex);
    
    Page<Libro> findByTituloContaining(String titulo, Pageable pageable);

    //@Query(value = "SELECT * FROM libro WHERE titulo ~ :regex AND estado = ?2", nativeQuery = true)
    //List<Libro> findByTituloRegex(@Param("regex") String regex, @Param("estado") String estado);

    @Query(value = "SELECT DISTINCT l.* FROM libro l  WHERE l.disponibles > 0", nativeQuery = true)
    Page<Libro> findByDisponibles(Pageable pageable);

    @Query(value = "SELECT DISTINCT l.* FROM libro l  WHERE l.disponibles > 0 AND l.titulo LIKE '%' || ?1 || '%'", nativeQuery = true)
    Page<Libro> findByTituloAndDisponibles(String titulo, Pageable pageable);
                   
    @Query(value = "SELECT * FROM libro l WHERE EXISTS " + 
    "(SELECT 1 " +
    "FROM ejemplar e " + 
    "JOIN prestamo p ON e.ejemplar_id = p.ejemplar_id " + 
    "WHERE e.libro_id = l.libro_id " +
    "AND p.matricula = ?1);", nativeQuery = true)
    List<Libro> findBooksByUser_id(@Param("matricula") Integer matricula);
                   
    boolean existsByIsbn(String isbn);
                   
    boolean existsByTitulo(String titulo);
    
    Optional<Libro> findById(int id);

    Optional<Libro> findByIsbn(String isbn);
    
    void deleteById(int id);
    
    boolean existsById(int id);
    
    
  

}
