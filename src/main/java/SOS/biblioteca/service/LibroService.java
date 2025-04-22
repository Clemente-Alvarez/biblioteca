package SOS.biblioteca.service;

import java.util.Optional;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import SOS.biblioteca.model.*;
import SOS.biblioteca.repository.LibroRepository;
import lombok.AllArgsConstructor;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class LibroService {

    private final LibroRepository repository;

    public Libro createLibro(Libro libro) {
        return repository.save(libro);
    }


}
