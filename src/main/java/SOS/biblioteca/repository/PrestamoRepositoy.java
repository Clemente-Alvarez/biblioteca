package SOS.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SOS.biblioteca.model.Prestamo;

public interface PrestamoRepositoy extends JpaRepository<Prestamo, Integer> {

}
