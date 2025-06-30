package SOS.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    boolean existsByMatricula(String matricula);

}