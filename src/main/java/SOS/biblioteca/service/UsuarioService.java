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

    public Usuario createUser(Usuario usuario) {
        return repository.save(usuario);
    }

    public List<Usuario> getAllUsuarios(){
        return repository.findAll(); 
    }

    public boolean existeUsuarioPorId(int id) {
        return repository.existsById(id);
    }

    public boolean existeUsuario(String nombre) {
        return repository.existsByNombre(nombre);
    }

    public Optional<Usuario> buscarPorId(int id) {
        return repository.findById(id);
    }

    public void eliminarEmpleado(int id) {
        repository.deleteById(id);
    }


    
}
