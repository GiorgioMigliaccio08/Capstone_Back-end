package giorgiomigliaccio.Capstone_Backend.Payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ArchiveCreatePayload {

    @NotNull(message = "Il luogo della visita è obbligatoria")

    String luogoVisita ;

    @NotNull(message = "La Data della visita è obbligatoria")

    LocalDate dataVisitaEffettuata;

    @NotNull(message = "La Data della visita di controllo è obbligatoria")

    LocalDate dataVisitaControllo;

    @NotNull(message = "Il Tipo di Visita è obbligatorio")
    @Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30")

    String tipoVisitaEffettuata;
    public  ArchiveCreatePayload (@NotNull(message = "Il luogo della visita è obbligatoria") String luogoVisita,
                                  @NotNull(message = "La Data della visita è obbligatoria") LocalDate dataVisitaEffettuata,
                                  @NotNull(message = "La Data della visita di controllo è obbligatoria") LocalDate dataVisitaControllo,
                                  @NotNull(message = "Il Tipo di Visita è obbligatorio") String tipoVisitaEffettuata ) {
        this.luogoVisita = luogoVisita;
        this.dataVisitaEffettuata=dataVisitaEffettuata;
        this.dataVisitaControllo= dataVisitaControllo;
        this.tipoVisitaEffettuata = tipoVisitaEffettuata;
    }



}
