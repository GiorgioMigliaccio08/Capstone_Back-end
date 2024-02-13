package giorgiomigliaccio.Capstone_Backend.Controls;

import giorgiomigliaccio.Capstone_Backend.Payloads.BookingCreatePayload;
import giorgiomigliaccio.Capstone_Backend.Services.BookingServices;
import giorgiomigliaccio.Capstone_Backend.entities.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class BookingController {
    @Autowired
    BookingServices bookingService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN','MEDICO')")
    public Page<Booking> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "")LocalDate data) {
        return bookingService.find(page, size, sortBy, data);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Booking getById(@PathVariable UUID id) throws Exception {
        return bookingService.findById(id);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@RequestBody BookingCreatePayload body, @PathVariable UUID userId) {
        return bookingService.create(body, userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Booking getByIdAndUpdate(@PathVariable UUID id, @RequestBody BookingCreatePayload body) {
        return bookingService.update(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable UUID id) {
        bookingService.delete(id);
    }
}
