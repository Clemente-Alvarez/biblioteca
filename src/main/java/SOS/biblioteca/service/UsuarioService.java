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

    public Usuario create(Usuario usuario) {
        return repository.save(usuario);
    }

    public List<Usuario> getAll(){
        return repository.findAll(); 
    }

    public boolean existePorId(int id) {
        return repository.existsById(id);
    }

    public boolean existe(String nombre) {
        return repository.existsByNombre(nombre);
    }

    public Optional<Usuario> buscarPorId(int id) {
        return repository.findById(id);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
    
}
