package giorgiomigliaccio.Capstone_Backend.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name= "archivio")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Archive {
    @Id
    @GeneratedValue
    private UUID id;
    private String luogoVisita;
    private LocalDate dataVisitaEffettuata;
    private LocalDate dataVisitaControllo;
    private String tipoVisitaEffettuata;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Archive(String luogoVisita, LocalDate dataVisitaEffettuata, LocalDate dataVisitaControllo,String tipoVisitaEffettuata ) {
        this.luogoVisita = luogoVisita;
        this.dataVisitaEffettuata = dataVisitaEffettuata;
        this.dataVisitaControllo = dataVisitaControllo;
        this.tipoVisitaEffettuata = tipoVisitaEffettuata;

    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            user.getDocumenti().add(this);
        }
    }
}
