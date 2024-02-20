package giorgiomigliaccio.Capstone_Backend.Controls;

import giorgiomigliaccio.Capstone_Backend.Payloads.ArchiveCreatePayload;
import giorgiomigliaccio.Capstone_Backend.Services.ArchiveServices;
import giorgiomigliaccio.Capstone_Backend.entities.Archive;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/archiviazione")
public class ArchiveController {
    @Autowired
    ArchiveServices archiveServices;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Page<Archive> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "")LocalDate dataVisitaEffettuata) {
        return archiveServices.find(page, size, sortBy, dataVisitaEffettuata);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public Archive getById(@PathVariable UUID id) throws Exception {
        return archiveServices.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Archive createDocument(@RequestBody ArchiveCreatePayload body,  @AuthenticationPrincipal User user) {
        return archiveServices.create(body, user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Archive getByIdAndUpdate(@PathVariable UUID id, @RequestBody ArchiveCreatePayload body) {
        return archiveServices.update(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable UUID id) {
        archiveServices.delete(id);
    }
}