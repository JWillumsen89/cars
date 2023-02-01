package dat3.cars.entity;


import jakarta.persistence.*;

@Entity
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "car_brand")
  private String brand;

  @Column(name = "car_model")
  private String model;

  @Column(name = "rental_price_day")
  private double pricePrDay;

  @Column(name = "max_discount")
  private double bestDiscount;

  public Car() {
  }

  public Car(String brand, String model, double pricePrDay, double bestDiscount) {
    this.brand = brand;
    this.model = model;
    this.pricePrDay = pricePrDay;
    this.bestDiscount = bestDiscount;
  }
}
