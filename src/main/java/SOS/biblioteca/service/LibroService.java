package SOS.biblioteca.service;

import java.util.List;
import java.util.Optional;

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

    public boolean existeLibroPorId(int id) {
        return repository.existsById(id);
    }

    public boolean existeLibroPorIsbn(String isbn) {
        return repository.existsByIsbn(isbn);
    }

    public Optional<Libro> buscarLibroPorId(int id){
        return repository.findById(id);
    }

    public void eliminarLibroPorId(int id){
        repository.deleteById(id);
    }

    public List<Libro> getAll(){
        return repository.findAll();
    }

    public List<Libro> getByTitulo(String titulo){
        return repository.findByTituloRegex(titulo); 
    }

    public List<Libro> getByTituloAndEstado(String titulo, String estado){
        return repository.findByTituloRegex(titulo , estado); 
    }

    // Devuelve los libros prestados previamente por un usuario. 
    public List<Libro> getLibrosPrestadosByUser_id(Integer matricula){
        return repository.findBooksByUser_id(matricula); 
    }

    public Page<Libro> buscarLibros(String titulo, String estado, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if(titulo != null && estado != null && !estado.isEmpty()) return repository.findByTituloAndEstado(titulo, estado, pageable);
        else if(titulo != null) return repository.findByTitulo(titulo, pageable);
        else if(estado != null && !estado.isEmpty) return repository.findByEstado(estado, pageable);
        else return repository.findAll(pageable);
    }

    public Page<Libro> buscarLibrosPorIdYEstado(Integer id, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByIdAndEstado(id, pageable);
    }
}
