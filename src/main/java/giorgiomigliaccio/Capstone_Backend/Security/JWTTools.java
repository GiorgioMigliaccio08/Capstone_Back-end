package giorgiomigliaccio.Capstone_Backend.Security;

import giorgiomigliaccio.Capstone_Backend.Exceptions.UnauthorizedException;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
@Component
public class JWTTools {

    private static String secret;
    private static int expiration;

    @Value("${spring.application.jwt.secret}")
    public void setSecret(String secretKey) {
        secret = secretKey;
    }

    @Value("${spring.application.jwt.expirationindays}")
    public void setExpiration(String expirationInDays) {
        expiration = Integer.parseInt(expirationInDays.trim()) * 24 * 60 * 60 * 1000;
    }


    static public String createToken(User u) {
        String token = Jwts.builder().setSubject(u.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
        return token;
    }

    static public void isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);

        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Il token non è valido");
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Il token è scaduto");
        } catch (Exception e) {
            throw new UnauthorizedException("Problemi col token, per favore effettua di nuovo il login");
        }
    }

    static public String extractSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
                .getBody().getSubject();
    }
}