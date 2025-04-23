package SOS.biblioteca.service;

import java.util.List;
import java.util.Optional;

import SOS.biblioteca.model.*;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import SOS.biblioteca.repository.EjemplarRepository;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class EjemplarService {
    
    private final EjemplarRepository repository;

    public Ejemplar create(Ejemplar ejemplar) {
        return repository.save(ejemplar);
    }

    public void delete(int idEjemplar) {
        repository.deleteById(idEjemplar);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    public Optional<Ejemplar> search(int id) {
        return repository.findById(id);
    }

    public List<Ejemplar> getByLibro_id(Integer libro_id){
        return repository.findByLibro_id(libro_id); 
    }

    public List<Ejemplar> getByLibro_idAndEstado(Integer libro_id, String estado){
        return repository.findByLibro_id(libro_id , estado); 
    }


}
