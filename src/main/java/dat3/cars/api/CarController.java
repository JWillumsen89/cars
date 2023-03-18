package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/cars")
public class CarController {

  CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  //ADMIN - MEMBER IF INCLUDE ALL IS FALSE
  /*
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  List<CarResponse> getCars() {
    return carService.getCars(true);
  }
   */
  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  @GetMapping
  List<CarResponse> getCars() {
    //Here I get the current authentication object
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    //Checking if it's an ADMIN or USER
    boolean isAdmin = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(role -> role.equals("ADMIN"));
    System.out.println(isAdmin);
    return carService.getCars(isAdmin);
  }


  //ADMIN - MEMBER IF INCLUDE ALL IS FALSE

  @GetMapping("/{id}")
  CarResponse getCarById(@PathVariable int id) throws Exception {
    return carService.getCarById(id, true);
  }

  //ANONYMOUS
  @PostMapping
  CarResponse addCar(@RequestBody CarRequest body) {
    return carService.addCar(body);
  }

  //ADMIN
  @PutMapping("/{id}")
  ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable int id) {
    return carService.updateCarDetails(body, id);
  }

  //ADMIN
  @PatchMapping("/bestDiscount/{id}/{value}")
  void setBestDiscount(@PathVariable int id, @PathVariable double value) {
    carService.setBestDiscount(id, value);
  }

  //ADMIN
  @DeleteMapping("/{id}")
  void deleteCarById(@PathVariable int id) {
    carService.deleteCarById(id);
  }

}
