package giorgiomigliaccio.Capstone_Backend.Services;

import giorgiomigliaccio.Capstone_Backend.Exceptions.NotFoundException;
import giorgiomigliaccio.Capstone_Backend.Payloads.BookingCreatePayload;
import giorgiomigliaccio.Capstone_Backend.Repositories.BookingRepository;
import giorgiomigliaccio.Capstone_Backend.Repositories.UserRepository;
import giorgiomigliaccio.Capstone_Backend.entities.Booking;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingServices {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Booking> find(int page, int size, String sortBy, LocalDate data) {
        if (size < 0)
            size = 10;
        if (size > 100)
            size = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        if (data != null) {
            return bookingRepository.findByData(data, pageable);
        } else {
            return bookingRepository.findAll(pageable);
        }
    }

    public Booking findById(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata!"));
    }

    public Booking create(BookingCreatePayload u, User user) {

        User utente = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new NotFoundException("Utente non trovato!"));


        Booking newPrenotazione = new Booking(u.getLuogo(), u.getData(), u.getTipoVisita());
        newPrenotazione.setUser(utente);
        return bookingRepository.save(newPrenotazione);
    }

    public Booking update(UUID id, BookingCreatePayload d) {
        Booking found = findById(id);
        found.setLuogo(d.getLuogo());
        found.setData(d.getData());
        found.setTipoVisita(d.getTipoVisita());
        return bookingRepository.save(found);
    }

    public void delete(UUID id) {
        Booking found = findById(id);
        bookingRepository.delete(found);
    }
}
