package dat3.cars.service.cars;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import dat3.cars.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CarServiceMockitoTest {

  @Mock
  CarRepository carRepository;

  CarService carService;

  @BeforeEach
  void setUp() {
    carService = new CarService(carRepository);
  }

  @Test
  void getCarsAdmin() {
    Car c1 = new Car("Toyota", "Camry", 500, 0.15);
    Car c2 = new Car("Honda", "Civic", 350, 0.1);
    c1.setCreated(LocalDateTime.now());
    c2.setCreated(LocalDateTime.now());

    //Mockito use
    Mockito.when(carRepository.findAll()).thenReturn(List.of(c1, c2));

    List<CarResponse> cars = carService.getCars(true);

    assertEquals(2, cars.size());
    assertNotNull(cars.get(0).getCreated());
  }

  @Test
  void getCarsNotAdmin() {
    Car c1 = new Car("Toyota", "Camry", 500, 0.15);
    Car c2 = new Car("Honda", "Civic", 350, 0.1);
    c1.setCreated(LocalDateTime.now());
    c2.setCreated(LocalDateTime.now());

    //Mockito use
    Mockito.when(carRepository.findAll()).thenReturn(List.of(c1, c2));

    List<CarResponse> cars = carService.getCars(false);

    assertEquals(2, cars.size());
    assertNull(cars.get(0).getCreated());
  }

  @Test
  void addCar() {
    Car c1 = new Car("Ferrari", "Enzo", 12000, 0.1);
    Mockito.when(carRepository.save(any(Car.class))).thenReturn(c1);

    CarRequest request = new CarRequest(c1);

    CarResponse response = carService.addCar(request);

    assertEquals("Enzo", response.getModel());
  }

}