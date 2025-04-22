package SOS.biblioteca.service;

import org.springframework.stereotype.Service;

import SOS.biblioteca.repository.LibroRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LibroService {

    private final LibroRepository repository;

}
