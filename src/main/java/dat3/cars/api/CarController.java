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

  @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
  @GetMapping("/{id}")
  CarResponse getCarById(@PathVariable int id) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    //Checking if it's an ADMIN or USER
    boolean isAdmin = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(role -> role.equals("ADMIN"));
    System.out.println(isAdmin);
    return carService.getCarById(id, isAdmin);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  CarResponse addCar(@RequestBody CarRequest body) {
    return carService.addCar(body);
  }

  //ADMIN
  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/{id}")
  ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable int id) {
    return carService.updateCarDetails(body, id);
  }

  //ADMIN
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/bestDiscount/{id}/{value}")
  void setBestDiscount(@PathVariable int id, @PathVariable double value) {
    carService.setBestDiscount(id, value);
  }

  //ADMIN
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  void deleteCarById(@PathVariable int id) {
    carService.deleteCarById(id);
  }

}
