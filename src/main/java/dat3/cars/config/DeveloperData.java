package dat3.cars.config;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import dat3.cars.service.ReservationService;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Configuration
public class DeveloperData implements ApplicationRunner {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  CarRepository carRepository;
  @Autowired
  UserWithRolesRepository userWithRolesRepository;
  @Autowired
  private ReservationRepository reservationRepository;
  @Autowired
  ReservationService reservationService = new ReservationService(reservationRepository, carRepository, memberRepository, userWithRolesRepository);


  private List<Car> cars;
  private List<Member> members;
  String passwordUsedByAll = "test12";


  @Override
  public void run(ApplicationArguments args) throws Exception {
    testData();
  }

  public void testData() {



    List<Car> cars = new ArrayList<>(Arrays.asList(
        new Car("Toyota", "Yaris", 450, 150),
        new Car("Ford", "Mondeo", 800, 200),
        new Car("Fiat", "Multipla", 600, 200),
        new Car("Citroen", "C5", 700, 200),
        new Car("Mercedes", "C-Klasse", 1200, 300),
        new Car("BMW", "5", 1200, 350),
        new Car("Honda", "Civic", 550, 160),
        new Car("Volkswagen", "Golf", 650, 180),
        new Car("Audi", "A4", 1000, 250),
        new Car("Hyundai", "Elantra", 500, 150),
        new Car("Mazda", "3", 600, 180),
        new Car("Kia", "Optima", 550, 170),
        new Car("Subaru", "Impreza", 650, 200),
        new Car("Lexus", "ES", 1100, 280),
        new Car("Jeep", "Cherokee", 800, 220),
        new Car("Volvo", "S60", 950, 240),
        new Car("Nissan", "Sentra", 550, 160),
        new Car("Chevrolet", "Cruze", 600, 180),
        new Car("Mitsubishi", "Lancer", 500, 150),
        new Car("Peugeot", "308", 700, 190),
        new Car("Renault", "Megane", 650, 180),
        new Car("Tesla", "Model S", 600, 220),
        new Car("Porsche", "911", 1200, 280),
        new Car("Jaguar", "XF", 1000, 230),
        new Car("Land Rover", "Range Rover", 1200, 290),
        new Car("Ferrari", "458 Italia", 900, 200),
        new Car("Maserati", "GranTurismo", 800, 190),
        new Car("Lamborghini", "Aventador", 1100, 250),
        new Car("Bugatti", "Chiron", 1600, 440),
        new Car("McLaren", "720S", 900, 200)
    ));


    members = new ArrayList<>(Arrays.asList(
        new Member("HansiUser", passwordUsedByAll, "h@ansi.dk", "Hans", "Zimmer", "Hansi Street", "Hanstown", "1000"),
        new Member("SvenneUser", passwordUsedByAll, "s@vend.dk", "Sven", "Sved", "Svengade", "Svendborg", "4400"),
        new Member("KarstennerUser", passwordUsedByAll, "k@arsten.dk", "Karsten", "Jensen", "Jensen vej", "København", "1205"),
        new Member("KarenMadUSer", passwordUsedByAll, "k@aren.dk", "Karen", "Malkeko", "Disney Hovedgade", "Disney", "9999")
    ));


    carRepository.saveAll(cars);
    memberRepository.saveAll(members);

    ReservationRequest r1 = new ReservationRequest(LocalDate.of(2023, 12, 24), cars.get(2).getId(), members.get(1).getUsername());
    ReservationRequest r2 = new ReservationRequest(LocalDate.of(2023, 12, 24), cars.get(1).getId(), members.get(2).getUsername());
    ReservationRequest r3 = new ReservationRequest(LocalDate.of(2023, 11, 24), cars.get(1).getId(), members.get(2).getUsername());
    makeReservations(r1);
    makeReservations(r2);
    makeReservations(r3);
    setupUserWithRoleUsers();
  }




  /*****************************************************************************************
   NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL
   iT'S ONE OF THE TOP SECURITY FLAWS YOU CAN DO
   *****************************************************************************************/
  private void setupUserWithRoleUsers() {

    System.out.println("******************************************************************************");
    System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
    System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
    System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
    System.out.println("******************************************************************************");
    UserWithRoles user1 = new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk");
    UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
    UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
    UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
    user1.addRole(Role.USER);
    user1.addRole(Role.ADMIN);
    user2.addRole(Role.USER);
    user3.addRole(Role.ADMIN);
    //No Role assigned to user4
    userWithRolesRepository.save(user1);
    userWithRolesRepository.save(user2);
    userWithRolesRepository.save(user3);
    userWithRolesRepository.save(user4);
  }


  public void makeReservations(ReservationRequest res) {

    try {
      reservationService.createReservation(res);
    } catch (ResponseStatusException e) {
      System.out.println(e.getMessage());
    }
  }
}
