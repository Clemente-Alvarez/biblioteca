package SOS.biblioteca.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
    // Buscar si existe por nombre de usuario
    // El nombre del campo tiene que ser el mismo que el campo de la tabla
    boolean existsByTitulo(String titulo);

    @Query(value = "SELECT * FROM libro WHERE titulo ~ :regex", nativeQuery = true)
    List<Libro> findByTituloRegex(@Param("regex") String regex);

    List<Libro> findByTituloContaining(String titulo, Pageable pageable);

    @Query(value = "SELECT * FROM libro WHERE titulo ~ :regex AND estado = ?2", nativeQuery = true)
    List<Libro> findByTituloRegex(@Param("regex") String regex, @Param("estado") String estado);

    @Query(value = "SELECT DISTINCT l.* FROM libro l JOIN ejemplar e ON e.libro_id = l.id WHERE e.estado = ?1", nativeQuery = true)
    List<Libro> findByEstado(String estado, Pageable pageable);

    @Query(value = "SELECT DISTINCT l.* FROM libro l JOIN ejemplar e " +
                   "ON e.libro_id = l.id WHERE e.estado = ?2" +
                   "AND l.titulo LIKE '%' || ?1 || '%'", nativeQuery = true)
    List<Libro> findByTituloAndEstado(String titulo, String estado, Pageable pageable);

    @Query(value = "SELECT DISTINCT l.* FROM libro l JOIN ejemplar e " +
                   "ON e.libro_id = l.id WHERE e.estado = 'disponible'" +
                   "AND l.id = ?1", nativeQuery = true)
    List<Libro> findByIdAndEstado(Integer id, Pageable pageable);

    @Query(value = "SELECT * FROM libro l WHERE EXISTS " + 
    "(SELECT 1 " +
    "FROM ejemplar e " + 
    "JOIN prestamo p ON e.ejemplar_id = p.ejemplar_id " + 
    "WHERE e.libro_id = l.libro_id " +
    "AND p.matricula = ?1);", nativeQuery = true)
    List<Libro> findBooksByUser_id(@Param("matricula") Integer matricula);

    boolean existsByIsbn(String isbn);

    boolean existsByTitulo(String titulo);

}
