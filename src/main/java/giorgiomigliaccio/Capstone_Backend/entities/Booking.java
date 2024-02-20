package giorgiomigliaccio.Capstone_Backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name= "prenotazioni")
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"user"})
public class Booking {
    @Id
    @GeneratedValue
    private UUID id;
    private String Luogo;
    private LocalDate Data;
    private String tipoVisita;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    public Booking(String luogo, LocalDate data, String tipoVisita) {
        this.Luogo = luogo;
        this.Data = data;
        this.tipoVisita = tipoVisita;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            user.getPrenotazioni().add(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLuogo() {
        return Luogo;
    }

    public void setLuogo(String luogo) {
        Luogo = luogo;
    }

    public LocalDate getData() {
        return Data;
    }

    public void setData(LocalDate data) {
        Data = data;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }
}
