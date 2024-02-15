package giorgiomigliaccio.Capstone_Backend.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor

public class AuthenticationSuccessfullPayload {
    private String accessToken;
}
