package dat3.cars.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @CreationTimestamp
  private LocalDate reservationDate;


  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate rentalDate;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "car_id")
  private Car car;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "username")
  private UserWithRoles member;

  public Reservation(int id, LocalDate rentalDate, Car car, Member member) {
    this.id = id;
    this.rentalDate = rentalDate;
    this.car = car;
    this.member = member;
  }

  public Reservation(LocalDate rentalDate, Car car, Member member) {
    this.rentalDate = rentalDate;
    this.car = car;
    this.member = member;
  }
}
