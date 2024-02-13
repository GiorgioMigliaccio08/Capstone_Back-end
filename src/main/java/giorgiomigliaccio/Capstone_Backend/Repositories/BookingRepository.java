package giorgiomigliaccio.Capstone_Backend.Repositories;

import giorgiomigliaccio.Capstone_Backend.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.UUID;

public interface BookingRepository extends JpaRepository <Booking , UUID> {
    Page<Booking> findByData(LocalDate data, Pageable pageable);

}