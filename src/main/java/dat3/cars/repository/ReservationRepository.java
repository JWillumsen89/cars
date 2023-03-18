package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

  boolean existsByCarAndAndRentalDate(Car car, LocalDate rentalDate);

  List<Reservation> findAllByMemberUsername(String username);
}
