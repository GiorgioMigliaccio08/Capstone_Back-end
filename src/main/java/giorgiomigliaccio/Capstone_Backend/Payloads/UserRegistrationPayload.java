package giorgiomigliaccio.Capstone_Backend.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegistrationPayload {
    @NotNull(message = "Il nome è obbligatorio")
    @Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30")

    String nome;
    @NotNull(message = "Il cognome è obbligatorio")

    String cognome;
    @NotNull(message = "Lo user name è obbligatorio")

    @Size(min = 3, max = 30, message = "Nome min 3 caratteri, massimo 30")
    String username;

    @Email(message = "Non hai inserito un indirizzo email valido")
    String email;
    @NotNull(message = "La password è obbligatoria")
    String password;

    public UserRegistrationPayload(@NotNull(message = "Il nome è obbligatorio") String nome, @NotNull(message = "Il cognome è obbligatorio") String cognome, @NotNull(message = "Lo user name è obbligatorio") String username, String email, @NotNull(message = "La password è obbligatoria") String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
