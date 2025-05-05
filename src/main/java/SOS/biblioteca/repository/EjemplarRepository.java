package SOS.biblioteca.repository;
import SOS.biblioteca.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {
    
    //Usar @Query no es necesario y tampoco se porque necesitamos este metodo


    @Query(value = "SELECT e.* FROM ejemplar e JOIN libro l " +
                   "ON e.libro_id = l.id AND l.titulo LIKE '%' || ?1 ||'%'", nativeQuery = true)
    Page<Ejemplar> findByTitulo(String titulo, Pageable pageable);

    @Query(value = "SELECT e.* FROM ejemplar e JOIN libro l " +
                   "ON e.libro_id = l.id AND l.titulo LIKE '%' || ?1 ||'%'" +
                   "AND e.estado = ?2", nativeQuery = true)
    Page<Ejemplar> findByTituloAndEstado(String titulo, String estado, Pageable pageable);

    Page<Ejemplar> findByUsuarioAndFechaUltimoPrestamo(Usuario usuario, String fechaUltimoPrestamo, Pageable pageable);

    Page<Ejemplar> findByUsuario(Usuario usuario, Pageable pageable);

    void deleteById(int idEjemplar);

    void deleteByLibroId(int idLibro);

    boolean existsById(int id);

    Optional<Ejemplar> findById(int id);

    Page<Ejemplar> findByLibroId(Integer libroId, Pageable pageable);

    Page<Ejemplar> findByEstado(String estado, Pageable pageable);

    
}   
