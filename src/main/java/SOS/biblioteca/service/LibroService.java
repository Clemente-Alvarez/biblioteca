package SOS.biblioteca.service;

import java.util.Optional;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import SOS.biblioteca.repository.LibroRepository;
import lombok.AllArgsConstructor;

import SOS.biblioteca.model.Libro;
import SOS.biblioteca.model.Usuario;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class LibroService {

    private final LibroRepository repository;

    public Libro createLibro(Libro libro) {
        return repository.save(libro);
    }

    public void deleteLibro(int idLibro) {
        repository.deleteById(idLibro);
    }

    public List<Libro> getAll(){
        return repository.findAll(); 
    }

    public boolean existeLibroPorId(int id) {
        return repository.existsById(id);
    }

    public boolean existeLibro(String nombre) {
        return repository.existsBytitulo(nombre);
    }

    public Optional<Usuario> buscarPorId(int id) {
        return repository.findById(id);
    }




}
