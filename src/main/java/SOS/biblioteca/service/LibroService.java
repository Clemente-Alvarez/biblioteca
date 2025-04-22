package SOS.biblioteca.service;

import java.util.Optional;
import java.util.List; 

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

    public Libro createLibro(Libro libro) {
        return repository.save(libro);
    }

    public List<Libro> getAll(){
        return repository.findAll();
    }

    public boolean existePorId(int id){
        return repository.existsById(id);
    }

    public boolean existe(String title) {
        return repository.existsByTitulo(title);
    }

    public Optional<Libro> buscarPorId(int id){
        return repository.findById(id);
    }

    public void delete(int id){
        repository.deleteById(id);
    }

}
