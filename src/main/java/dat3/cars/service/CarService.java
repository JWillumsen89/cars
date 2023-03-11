package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {

  CarRepository carRepository;

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<CarResponse> getCars(boolean includeAll) {
    List<Car> cars = carRepository.findAll();

    return cars.stream().map(c -> new CarResponse(c, includeAll)).toList();
  }

  public CarResponse getCarById(int id, boolean includeAll) {
    Car car = carRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));
    return new CarResponse(car, includeAll);
  }

  public CarResponse addCar(CarRequest carRequest) {
    Car newCar = CarRequest.getCarEntity(carRequest);
    newCar = carRepository.save(newCar);

    return new CarResponse(newCar, true);
  }

  public ResponseEntity<Boolean> updateCarDetails(CarRequest body, int id) {
    Car updateCar = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with that ID not found"));
    updateCar.setBrand(body.getBrand());
    updateCar.setModel(body.getModel());
    updateCar.setPricePrDay(body.getPricePrDay());
    updateCar.setBestDiscount(body.getBestDiscount());

    carRepository.save(updateCar);
    return ResponseEntity.ok(true);
  }

  public void setBestDiscount(int id, double value) {
    Car car = carRepository.getReferenceById(id);
    car.setBestDiscount(value);
    carRepository.save(car);
  }

  public void deleteCarById(int id) {
    carRepository.deleteById(id);
  }

  public List<CarResponse> findByBrandAndModel(String brand, String model, boolean includeAll) {
    List<Car> cars = carRepository.findCarByBrandAndModel(model,brand);
    return cars.stream().map(c -> new CarResponse(c, includeAll)).toList();
  }
}
