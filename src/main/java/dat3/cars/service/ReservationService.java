package dat3.cars.service;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final CarRepository carRepository;
  private final MemberRepository memberRepository;
  private final UserWithRolesRepository userWithRolesRepository;

  public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository, MemberRepository memberRepository, UserWithRolesRepository userWithRolesRepository) {
    this.reservationRepository = reservationRepository;
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
    this.userWithRolesRepository = userWithRolesRepository;
  }


  public Reservation createReservation(ReservationRequest rq) {
    Car car = carRepository.findById(rq.getCarId()).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no car with this id"));
    Member member = memberRepository.findById(rq.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is no member with that username"));

    if (reservationRepository.existsByCarAndAndRentalDate(car, rq.getRentalDate())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already a reservation for that car on that date.");
    }
    if (rq.getRentalDate().isBefore(LocalDate.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cant make a reservation back in time");
    }
    if (rq.getRentalDate() == null || rq.getCarId() == 0 || rq.getUsername() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Information is missing");
    }

    return reservationRepository.save(new Reservation(rq.getRentalDate(), car, member));
  }

  public List<ReservationResponse> getReservations() {
    List<Reservation> reservations = reservationRepository.findAll();

    return reservations.stream().map(ReservationResponse::new).toList();
  }

}
