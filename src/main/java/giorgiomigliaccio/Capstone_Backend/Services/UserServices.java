package giorgiomigliaccio.Capstone_Backend.Services;

import giorgiomigliaccio.Capstone_Backend.Exceptions.BadRequestException;
import giorgiomigliaccio.Capstone_Backend.Exceptions.NotFoundException;
import giorgiomigliaccio.Capstone_Backend.Payloads.UserRegistrationPayload;
import giorgiomigliaccio.Capstone_Backend.Repositories.UserRepository;
import giorgiomigliaccio.Capstone_Backend.entities.Archive;
import giorgiomigliaccio.Capstone_Backend.entities.Booking;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@Service
public class UserServices {
    @Autowired
    private PasswordEncoder bCrypt;
    @Autowired
    private UserRepository userRepository;


    public Page<User> findAllUsers(int page, int size, String sortBy) {
        if (size < 0)
            size = 10;
        if (size > 100)
            size = 20;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    public User createUser(UserRegistrationPayload payload) {
        String email = payload.getEmail();

        if (!isValidEmail(email)) {
            throw new BadRequestException("L'email inserita non è valida.");
        }

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new NotFoundException("Utente non trovato con ID: " + user.getUserId());
        });

        User newUser = new User( payload.getNome(),payload.getCognome(), payload.getUsername(), payload.getEmail(), payload.getPassword() );
        return userRepository.save(newUser);
    }

    private boolean isValidEmail(String email) {
        return email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
    }

    public User findUserById(UUID id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato con ID: " + id));
    }

    public User findUserByIdAndUpdate(UUID id, UserRegistrationPayload payload) throws NotFoundException {
        User foundUser = findUserById(id);
        foundUser.setUserId(id);
        foundUser.setNome(payload.getNome());
        foundUser.setCognome(payload.getCognome());
        foundUser.setUsername(payload.getUsername());
        foundUser.setEmail(payload.getEmail());
        foundUser.setPassword(bCrypt.encode(payload.getPassword()));


        return userRepository.save(foundUser);
    }

    public void findUserByIdAndDelete(UUID id) throws NotFoundException {
        User foundUser = findUserById(id);
        userRepository.delete(foundUser);
    }

    public User findUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente non trovato con email: " + email));
    }

    public User findUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Utente non trovato con username: " + username));
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente corrente non trovato"));
    }

    public List<Booking> getbookingList (User user){
        return userRepository.getAllBookings(user);
    }

    public List<Archive> getarchiveList (User user){
        return userRepository.getAllDocuments(user);
    }

}
