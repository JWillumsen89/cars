package dat3.cars.service.reservations;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import dat3.cars.service.ReservationService;
import dat3.security.repository.UserWithRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;



@DataJpaTest
class ReservationServiceH2Test {

  @Autowired
  ReservationRepository reservationRepository;

  @Autowired
  CarRepository carRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  UserWithRolesRepository userWithRolesRepository;

  ReservationService reservationService;
  @BeforeEach
  void setUp() {
    reservationService = new ReservationService(reservationRepository,carRepository,memberRepository,userWithRolesRepository);
  }

  @Test
  void doubleBookingException() {
    Car c1 = new Car("Toyota", "Yaris", 250,0.2);
    carRepository.save(c1);
    Member m1 = new Member("m1", "test12", "m1@a.dk", "aa", "Olsen",
        "xx vej 34", "Lyngby", "2800");
    memberRepository.save(m1);
    ReservationRequest res1 = new ReservationRequest(LocalDate.of(2023, 12, 24), c1.getId(), m1.getUsername());
    reservationService.createReservation(res1);
    //Using the same reservation (res1) one more time, to make sure I cant make a double reservation
    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> reservationService.createReservation(res1));
    assertEquals("400 BAD_REQUEST \"There is already a reservation for that car on that date.\"", exception.getMessage());

  }
}