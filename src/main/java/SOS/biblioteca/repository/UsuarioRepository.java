package SOS.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import SOS.biblioteca.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    // Buscar si existe por nombre de usuario
    // El nombre del campo tiene que ser el mismo que el campo de la tabla
    boolean existsByNombre(String nombre);
    Optional<Usuario> findByMatricula(int id);
    boolean existsByCorreo(String correo);

}