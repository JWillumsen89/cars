package dat3.cars.dto;


import dat3.cars.entity.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CarRequest {

  private int id;
  private String brand;
  private String model;
  private double pricePrDay;
  private double bestDiscount;

  public static Car getCarEntity(CarRequest c) {
    return new Car(c.id, c.brand, c.model, c.pricePrDay, c.bestDiscount);
  }

  public CarRequest(String brand, String model, double pricePrDay, double bestDiscount) {
    this.brand = brand;
    this.model = model;
    this.pricePrDay = pricePrDay;
    this.bestDiscount = bestDiscount;
  }

  public CarRequest(Car c) {
    this.id = c.getId();
    this.brand = c.getBrand();
    this.model = c.getModel();
    this.pricePrDay = c.getPricePrDay();
    this.bestDiscount = c.getBestDiscount();
  }

}
