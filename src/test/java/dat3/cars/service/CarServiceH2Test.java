package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarServiceH2Test {

  @Autowired
  CarRepository carRepository;

  CarService carService;

  boolean dataIsReady = false;
  @BeforeEach
  void setUp() {
    if (!dataIsReady) {
      carRepository.save(new Car("Toyota", "Yaris", 450, 0.20));
      carRepository.save(new Car("Mercedes", "C-Klasse", 1200, 0.20));
      dataIsReady = true;
      carService = new CarService(carRepository);
    }
  }

  @Test
  void getCarsAdmin() {
    List<CarResponse> cars = carService.getCars(true);
    assertEquals(2, cars.size());
    assertNotNull(cars.get(0).getBestDiscount());
  }

  @Test
  void getCarsNotAdmin() {
    List<CarResponse> cars = carService.getCars(false);
    assertEquals(2, cars.size());
    assertNull(cars.get(0).getBestDiscount());
  }

  /*
  @Test
  void getCarById() {
    CarResponse car = carService.getCarById(2, true);
    assertEquals("Mercedes",car.getBrand());
  }

   */

  @Test
  void addCar() {
    CarRequest newCar = new CarRequest("Honda", "Civic", 650);
    carService.addCar(newCar);
    List<CarResponse> cars = carService.getCars(true);
    assertEquals(3, cars.size());
    assertEquals("Honda", cars.get(2).getBrand());
  }
/*
  @Test
  void setBestDiscount() {
    carService.setBestDiscount(1,0.5);
    CarResponse car = carService.getCarById(1, true);
    assertEquals(0.5, car.getBestDiscount());
  }

 */

  @Test
  void deleteCarById() {
    Car car = carRepository.save(new Car("Toyota", "Camry", 500, 0.15));
    carService.deleteCarById(car.getId());
    assertFalse(carRepository.existsById(car.getId()));
  }
}