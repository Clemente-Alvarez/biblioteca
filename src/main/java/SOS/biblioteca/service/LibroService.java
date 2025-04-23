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

    public Libro create(Libro libro) {
        return repository.save(libro);
    }

    public boolean exists(String titulo) {
        return repository.existsByTitulo(titulo);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    public Optional<Libro> search(int id){
        return repository.findById(id);
    }

    public void delete(int id){
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

}
