package SOS.biblioteca.service;

import java.util.List;
import java.util.Optional;

import SOS.biblioteca.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import SOS.biblioteca.repository.EjemplarRepository;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class EjemplarService {

    private final EjemplarRepository repository;

    public Ejemplar crearEjemplar(Ejemplar ejemplar) {
        return repository.save(ejemplar);
    }

    public void eliminarEjemplarPorId(int idEjemplar) {
        repository.deleteById(idEjemplar);
    }

    public void eliminarEjemplarPorLibroId(int idLibro){
        repository.deleteByLibroId(idLibro);
    }   

    public boolean existeEjemplarPorId(int id) {
        return repository.existsById(id);
    }

    public Optional<Ejemplar> buscarEjemplarPorId(int id) {
        return repository.findById(id);
    }

    public Optional<Ejemplar> buscarEjemplarPorLibroIdYEstado(int id, String estado) {
        return repository.findByLibroIdAndEstado(id, estado);
    }

    public Page<Ejemplar> buscarEjemplaresPorLibroId(Integer libroId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByLibroId(libroId,pageable);
    }

    public Page<Ejemplar> getByEstado(String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByEstado(estado, pageable);
    }


    public Page<Ejemplar> buscarEjemplares(String titulo, String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (titulo != null && estado != null && !estado.isEmpty())
            return repository.findByTituloAndEstado(titulo, estado, pageable);
        else if (titulo != null)
            return repository.findByTitulo(titulo, pageable);
        else if (estado != null && !estado.isEmpty())
            return repository.findByEstado(estado, pageable);
        else
            return repository.findAll(pageable);
    }

}
