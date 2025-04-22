package SOS.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SOS.biblioteca.model.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer>{
    
}
