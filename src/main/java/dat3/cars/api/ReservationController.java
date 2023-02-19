package dat3.cars.api;


import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Reservation;
import dat3.cars.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

  ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  List<ReservationResponse> getReservations() {
    return reservationService.getReservations();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  Reservation createReservation(@RequestBody ReservationRequest reservationRequest) {
    return reservationService.createReservation(reservationRequest);
  }
}
