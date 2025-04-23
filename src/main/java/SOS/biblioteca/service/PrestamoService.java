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

    public Prestamo create(Prestamo prestamo) {
        return repository.save(prestamo);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    public Optional<Prestamo> search(int id) {
        return repository.findById(id);
    }

    public List<Prestamo> getByUser_id(Integer user_id){
        return repository.findByUser_id(user_id); 
    }

    // Devuelve una lista de prestamos filtrado por el estado y matricula del usuario. 
    public List<Prestamo> getByUser_idAndStatus(Integer user_id, Integer devuelto){
        return repository.findByUser_id(user_id , devuelto); 
    }

    // Devuelve una lista de prestamos activos dado un intervalo de fechas y una matricula
    public List<Prestamo> getByInterval(Integer user_id, LocalDate startDate, LocalDate endDate){
        return repository.findByIntervalAndUser_id(user_id, startDate, endDate); 
    }
}
