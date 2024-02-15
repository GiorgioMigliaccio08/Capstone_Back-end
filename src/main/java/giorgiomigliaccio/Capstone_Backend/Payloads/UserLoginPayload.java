package giorgiomigliaccio.Capstone_Backend.Payloads;

import lombok.Getter;

@Getter
public class UserLoginPayload {
    String username;

    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
