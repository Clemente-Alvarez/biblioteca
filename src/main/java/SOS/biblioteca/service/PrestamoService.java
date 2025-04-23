package SOS.biblioteca.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import SOS.biblioteca.model.Prestamo;

import SOS.biblioteca.repository.PrestamoRepository;
import lombok.AllArgsConstructor;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class PrestamoService {
     private final PrestamoRepository repository;

    public Prestamo createPrestamo(Prestamo prestamo) {
        return repository.save(prestamo);
    }

    public List<Prestamo> getByUser_id(Integer user_id){
        return repository.findByUser_id(user_id); 
    }

    // Devuelve una lista de prestamos filtrado por el estado y matricula del usuario. 
    public List<Prestamo> getByUser_id(Integer user_id, Integer devuelto){
        return repository.findByUser_id(user_id , devuelto); 
    }

    public List<Prestamo> getByInterval(LocalDate startDate, LocalDate endDate){
        return repository.findByInterval(startDate, endDate); 
    }

    public boolean existePrestamoPorId(int id) {
        return repository.existsById(id);
    }

    public Optional<Prestamo> buscarPorId(int id) {
        return repository.findById(id);
    }


}
