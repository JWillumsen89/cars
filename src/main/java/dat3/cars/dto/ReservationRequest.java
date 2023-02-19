package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.security.entity.UserWithRoles;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

  private int id;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate rentalDate;
  private int carId;
  private String username;


  public ReservationRequest(LocalDate rentalDate, int carId, String username) {
    this.rentalDate = rentalDate;
    this.carId = carId;
    this.username = username;
  }
}
