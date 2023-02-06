package dat3.cars.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "car_brand", nullable = false, length = 50)
  private String brand;

  @Column(name = "car_model", nullable = false, length = 60)
  private String model;

  @Column(name = "rental_price_day")
  private double pricePrDay;

  @Column(name = "max_discount")
  private double bestDiscount;

  @Column(name = "created")
  @CreationTimestamp
  private LocalDateTime created;

  @Column(name = "last_edited")
  @UpdateTimestamp
  private LocalDateTime lastEdited;
  public Car(String brand, String model, double pricePrDay, double bestDiscount) {
    this.brand = brand;
    this.model = model;
    this.pricePrDay = pricePrDay;
    this.bestDiscount = bestDiscount;
  }

  public Car(int id, String brand, String model, double pricePrDay) {
    this.id = id;
    this.brand = brand;
    this.model = model;
    this.pricePrDay = pricePrDay;
  }
}
