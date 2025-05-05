package SOS.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {
    
    //Usar @Query no es necesario y tampoco se porque necesitamos este metodo
    @Query(value = "SELECT * FROM ejemplar WHERE libro_id = ?1", nativeQuery = true)
    Page<Ejemplar> findByLibroId(Integer id, Pageable pageable);

    //Usar @Query no es necesario
    @Query(value = "SELECT * FROM ejemplar WHERE estado = ?1", nativeQuery = true)
    Page<Ejemplar> findByEstado(String estado, Pageable pageable);

    @Query(value = "SELECT e.* FROM ejemplar e JOIN libro l " +
                   "WHERE e.libro_id = l.id AND l.titulo LIKE '%' || ?1 ||'%'", nativeQuery = true)
    List<Ejemplar> findByTitulo(String titulo, Pageable pageable);

    @Query(value = "SELECT e.* FROM ejemplar e JOIN libro l " +
                   "WHERE e.libro_id = l.id AND l.titulo LIKE '%' || ?1 ||'%'" +
                   "AND e.estado = ?2", nativeQuery = true)
    List<Ejemplar> findByTituloAndEstado(String titulo, String estado, Pageable pageable);
}   
