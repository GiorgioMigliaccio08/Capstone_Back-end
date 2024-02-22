package giorgiomigliaccio.Capstone_Backend.Controls;


import giorgiomigliaccio.Capstone_Backend.Exceptions.NotFoundException;
import giorgiomigliaccio.Capstone_Backend.Payloads.UserRegistrationPayload;
import giorgiomigliaccio.Capstone_Backend.Repositories.UserRepository;
import giorgiomigliaccio.Capstone_Backend.Services.BookingServices;
import giorgiomigliaccio.Capstone_Backend.Services.UserServices;
import giorgiomigliaccio.Capstone_Backend.entities.Archive;
import giorgiomigliaccio.Capstone_Backend.entities.Booking;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    BookingServices bookingServices;

    @Autowired
    UserRepository userRepository;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Validated UserRegistrationPayload body) {
        return userServices.createUser(body);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "idUser") String sortBy) {
        return userServices.findAllUsers(page, size, sortBy);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) throws NotFoundException {
        return userServices.findUserById(userId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable UUID userId, @RequestBody UserRegistrationPayload body)
            throws NotFoundException {
        return userServices.findUserByIdAndUpdate(userId, body);
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) throws NotFoundException {
        userServices.findUserByIdAndDelete(userId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userServices.findUserByUsername(username);
    }
    @GetMapping("/bookings")
    public List <Booking> getbookings (@AuthenticationPrincipal User user){
        return userServices.getbookingList(user);
    }

    @GetMapping("/documents")
    public List <Archive> getdocuments (@AuthenticationPrincipal User user){
        return userServices.getarchiveList(user);
    }
}
