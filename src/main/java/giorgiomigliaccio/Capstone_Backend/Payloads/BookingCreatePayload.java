package giorgiomigliaccio.Capstone_Backend.Payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class BookingCreatePayload {

    @NotNull(message = "Il luogo della visita è obbligatoria")

    String Luogo ;

    @NotNull(message = "La Data della visita è obbligatoria")

    LocalDate Data;


    @NotNull(message = "Il Tipo di Visita è obbligatorio")
    @Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30")

    String tipoVisita;
    public  BookingCreatePayload (@NotNull(message = "Il luogo della visita è obbligatoria") String Luogo,
                                  @NotNull(message = "La Data della visita è obbligatoria") LocalDate Data,
                                  @NotNull(message = "Il Tipo di Visita è obbligatorio") String tipoVisita ) {
        this.Luogo = Luogo;
        this.Data=Data;
        this.tipoVisita = tipoVisita;
    }

}
