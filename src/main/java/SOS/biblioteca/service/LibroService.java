package SOS.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import SOS.biblioteca.repository.LibroRepository;
import SOS.biblioteca.model.Libro;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class LibroService {

    private final LibroRepository repository;

    public Libro crearLibro(Libro libro) {
        return repository.save(libro);
    }

    public boolean existeLibroPorTitulo(String titulo) {
        return repository.existsByTitulo(titulo);
    }

    public boolean existeLibroPorId(Integer id) {
        return repository.existsById(id);
    }

    public boolean existeLibroPorIsbn(String isbn) {
        return repository.existsByIsbn(isbn);
    }

    public Optional<Libro> buscarLibroPorId(Integer id){
        return repository.findById(id);
    }

    public Optional<Libro> buscarLibroPorIsbn(String isbn){
        return repository.findByIsbn(isbn);
    }

    public void eliminarLibroPorId(Integer id){
        repository.deleteById(id);
    }

    public List<Libro> getAll(){
        return repository.findAll();
    }

    // Devuelve los libros prestados previamente por un usuario. 
    public List<Libro> getLibrosPrestadosByUser_id(Integer matricula){
        return repository.findBooksByUser_id(matricula); 
    }

    public Page<Libro> buscarLibrosPorDisponibles(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByDisponibles(pageable);
    }

    public Page<Libro> buscarLibros(String titulo, Boolean disponible, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if(titulo != null && disponible) return repository.findByTituloAndDisponibles(titulo, pageable);
        else if(titulo != null) return repository.findByTituloContaining(titulo, pageable);
        else if(disponible) return repository.findByDisponibles(pageable);
        else return repository.findAll(pageable);
    }
}
