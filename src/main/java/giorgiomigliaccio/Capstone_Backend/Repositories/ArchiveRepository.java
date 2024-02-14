package giorgiomigliaccio.Capstone_Backend.Repositories;

import giorgiomigliaccio.Capstone_Backend.entities.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.UUID;

public interface ArchiveRepository extends JpaRepository<Archive, UUID> {
    Page<Archive> findByDataVisitaEffettuata(LocalDate data, Pageable pageable);

}
