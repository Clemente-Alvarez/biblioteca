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

    public Libro createLibro(Libro libro) {
        return repository.save(libro);
    }

    public List<Libro> getAll(){
        return repository.findAll();
    }

    public List<Libro> getByTitulo(String titulo){
        return repository.findByTituloRegex(titulo); 
    }

    public List<Libro> getByTitulo(String titulo, String estado){
        return repository.findByTituloRegex(titulo , estado); 
    }

    public boolean existeLibroPorId(int id) {
        return repository.existsById(id);
    }

    public Optional<Libro> buscarPorId(int id) {
    public boolean existe(String title) {
        return repository.existsBytitulo(title);
    }

    public Optional<Libro> buscarPorId(int id){
        return repository.findById(id);
    }

    // Devuelve los libros prestados previamente por un usuario. 
    public List<Libro> getLibrosPrestadosByUser_id(Integer matricula){
        return repository.findBooksByUser_id(matricula); 
    }
    public void delete(int id){
        repository.deleteById(id);
    }

}
