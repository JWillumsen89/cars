package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.util.*;


@Controller
public class DeveloperData implements ApplicationRunner {


  final CarRepository carRepository;
  final MemberRepository memberRepository;

  public DeveloperData(CarRepository carRepository, MemberRepository memberRepository) {
    this.carRepository = carRepository;
    this.memberRepository = memberRepository;
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    testData();
  }

  public void testData() {
    String passwordUsedByAll = "test12";

    Map<String, String> phones = new HashMap<>();
    phones.put("Work", "123456");
    phones.put("mobile", "987654321");


    List<Car> cars = new ArrayList<>(Arrays.asList(
        new Car("Toyota", "Yaris", 450, 0.20),
        new Car("Ford", "Mondeo", 800, 0.20),
        new Car("Fiat", "Multipla", 600, 0.20),
        new Car("Citroen", "C5", 700, 0.20),
        new Car("Mercedes", "C-Klasse", 1200, 0.20),
        new Car("BMW", "5", 1200, 0.20)
    ));

    List<Member> members = new ArrayList<>(Arrays.asList(
        new Member("Hansi", passwordUsedByAll, "h@ansi.dk", "Hans", "Zimmer", "Hansi Street", "Hanstown", "1000"),
        new Member("Sven", passwordUsedByAll, "s@vend.dk", "Sven", "Sved", "Svengade", "Svendborg", "4400"),
        new Member("Karsten", passwordUsedByAll, "k@arsten.dk", "Karsten", "Jensen", "Jensen vej", "København", "1205"),
        new Member("Karen", passwordUsedByAll, "k@aren.dk", "Karen", "Malkeko", "Disney Hovedgade", "Disney", "9999")
    ));

    for (Member member : members) {
      List<String> favoriteCarColors = new ArrayList<>(Arrays.asList("Orange", "Blue", "Black"));

      member.setPhones(phones);
      member.setFavoriteCarColors(favoriteCarColors);
    }
    carRepository.saveAll(cars);
    memberRepository.saveAll(members);
  }
}
