package giorgiomigliaccio.Capstone_Backend.Security;

import giorgiomigliaccio.Capstone_Backend.Exceptions.NotFoundException;
import giorgiomigliaccio.Capstone_Backend.Exceptions.UnauthorizedException;
import giorgiomigliaccio.Capstone_Backend.Services.UserServices;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JWTAuthFilter  extends OncePerRequestFilter {
    @Autowired
    UserServices userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer "))
                throw new UnauthorizedException("Per favore aggiungi il token all'authorization header");

            String accessToken = authHeader.substring(7);

            JWTTools.isTokenValid(accessToken);

            String username = JWTTools.extractSubject(accessToken);
            try {
                User user = userService.findUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

                filterChain.doFilter(request, response);

            }catch (NotFoundException e) {

                e.printStackTrace();
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}