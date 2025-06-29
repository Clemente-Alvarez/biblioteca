package SOS.biblioteca.service;

import java.util.Optional;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import SOS.biblioteca.model.Usuario;
import SOS.biblioteca.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario crearUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public boolean existeUsuarioPorMatricula(Integer id) {
        return repository.existsById(id);
    }

    public boolean existeUsuarioPorNombre(String nombre) {
        return repository.existsByNombre(nombre);
    }

    public boolean existeUsuarioPorCorreo(String correo) {
        return repository.existsByCorreo(correo);
    }

    public Optional<Usuario> buscarUsuarioPorMatricula(Integer id) {
        return repository.findById(id);
    }

    public void eliminarUsuarioPorMatricula(Integer id) {
        repository.deleteById(id);
    }
    
    public Page<Usuario> buscarUsuarios(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable); 
    }
}
