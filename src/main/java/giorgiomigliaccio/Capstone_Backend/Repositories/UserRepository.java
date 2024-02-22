package giorgiomigliaccio.Capstone_Backend.Repositories;


import giorgiomigliaccio.Capstone_Backend.entities.Archive;
import giorgiomigliaccio.Capstone_Backend.entities.Booking;
import giorgiomigliaccio.Capstone_Backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <User, UUID>{
    Optional <User> findByEmail (String email);
    Optional <User> findByUsername (String username);

    @Query("SELECT b FROM Booking b WHERE b.user= :user")
    List<Booking> getAllBookings(@Param("user") User user);

    @Query("SELECT b FROM Archive b WHERE b.user= :user")
    List<Archive> getAllDocuments(@Param("user") User user);
}
