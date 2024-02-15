package giorgiomigliaccio.Capstone_Backend.Security;

import giorgiomigliaccio.Capstone_Backend.Exceptions.NotFoundException;
import giorgiomigliaccio.Capstone_Backend.Exceptions.UnauthorizedException;
import giorgiomigliaccio.Capstone_Backend.Payloads.AuthenticationSuccessfullPayload;
import giorgiomigliaccio.Capstone_Backend.Payloads.UserLoginPayload;
import giorgiomigliaccio.Capstone_Backend.Payloads.UserRegistrationPayload;
import giorgiomigliaccio.Capstone_Backend.Services.UserServices;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserServices userService;
    @Autowired
    private PasswordEncoder bcrypt;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Validated UserRegistrationPayload body) {

        body.setPassword(bcrypt.encode(body.getPassword()));
        User createdUser = userService.createUser(body);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody UserLoginPayload body)
            throws NotFoundException {

        User user = userService.findUserByUsername(body.getUsername());

        String plainPW = body.getPassword();
        String hashedPW = user.getPassword();

        if (!bcrypt.matches(plainPW, hashedPW))
            throw new UnauthorizedException("Credenziali non valide");

        String token = JWTTools.createToken(user);

        return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token), HttpStatus.OK);
    }

}
