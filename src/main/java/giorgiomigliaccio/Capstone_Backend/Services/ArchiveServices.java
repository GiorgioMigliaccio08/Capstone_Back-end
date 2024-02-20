package giorgiomigliaccio.Capstone_Backend.Services;

import giorgiomigliaccio.Capstone_Backend.Exceptions.NotFoundException;
import giorgiomigliaccio.Capstone_Backend.Payloads.ArchiveCreatePayload;
import giorgiomigliaccio.Capstone_Backend.Repositories.ArchiveRepository;
import giorgiomigliaccio.Capstone_Backend.Repositories.UserRepository;
import giorgiomigliaccio.Capstone_Backend.entities.Archive;
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
public class ArchiveServices {

    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Archive> find(int page, int size, String sortBy, LocalDate dataVisitaEffettuata) {
        if (size < 0)
            size = 10;
        if (size > 100)
            size = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        if (dataVisitaEffettuata != null) {
            return  archiveRepository.findByDataVisitaEffettuata(dataVisitaEffettuata, pageable);
        } else {
            return archiveRepository.findAll(pageable);
        }
    }

    public Archive findById(UUID id) {
        return archiveRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Documentazione non trovata!"));
    }

    public Archive create(ArchiveCreatePayload u, User user) {

        User utente = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new NotFoundException("Utente non trovato!"));


        Archive newDocumento = new Archive(u.getLuogoVisita(), u.getDataVisitaEffettuata(), u.getDataVisitaControllo(),u.getTipoVisitaEffettuata());
        newDocumento.setUser(utente);
        return archiveRepository.save(newDocumento);
    }

    public Archive update(UUID id, ArchiveCreatePayload d) {
        Archive found = findById(id);
        found.setLuogoVisita(d.getLuogoVisita());
        found.setDataVisitaEffettuata(d.getDataVisitaEffettuata());
        found.setDataVisitaControllo(d.getDataVisitaControllo());
        found.setTipoVisitaEffettuata(d.getTipoVisitaEffettuata());
        return archiveRepository.save(found);
    }

    public void delete(UUID id) {
        Archive found = findById(id);
        archiveRepository.delete(found);
    }
}
