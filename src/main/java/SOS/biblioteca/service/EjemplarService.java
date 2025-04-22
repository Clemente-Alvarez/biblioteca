package SOS.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import SOS.biblioteca.model.Ejemplar;
import SOS.biblioteca.repository.EjemplarRepository;

@Service
@AllArgsConstructor
public class EjemplarService {
        private final EjemplarRepository repository;

    public Ejemplar create(Ejemplar ejemplar) {
        return repository.save(ejemplar);
    }

    public List<Ejemplar> getAll(){
        return repository.findAll();
    }

    public boolean existePorId(int id){
        return repository.existsById(id);
    }

    public Optional<Ejemplar> buscarPorId(int id){
        return repository.findById(id);
    }

    public void delete(int id){
        repository.deleteById(id);
    }
    
}
