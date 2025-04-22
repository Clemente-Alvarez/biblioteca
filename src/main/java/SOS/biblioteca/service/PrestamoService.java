package SOS.biblioteca.service;

import java.util.Optional;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import SOS.biblioteca.repository.PrestamoRepositoy;
import SOS.biblioteca.model.Prestamo;
import SOS.biblioteca.model.Usuario;

@Service
@AllArgsConstructor
public class PrestamoService {

    private final PrestamoRepositoy repository;

    public Prestamo create(Prestamo prestamo) {
        return repository.save(prestamo);
    }

    public List<Prestamo> getAll(){
        return repository.findAll();
    }

    public boolean existePorId(int id){
        return repository.existsById(id);
    }

    public Optional<Prestamo> buscarPorId(int id){
        return repository.findById(id);
    }

    public void delete(int id){
        repository.deleteById(id);
    }
    
}
