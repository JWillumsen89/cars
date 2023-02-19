package dat3.cars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.security.entity.UserWithRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponse {

  private int id;
  @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
  private LocalDate rentalDate;
  private int carId;
  private String brand;
  private String model;

  public ReservationResponse(Reservation r) {
    this.id = r.getId();
    this.carId = r.getCar().getId();
    this.brand = r.getCar().getBrand();
    this.model = r.getCar().getModel();
    this.rentalDate = r.getRentalDate();
  }
}
