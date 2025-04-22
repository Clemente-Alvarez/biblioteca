package SOS.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SOS.biblioteca.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
