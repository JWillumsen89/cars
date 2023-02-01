package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    List<Car> cars = new ArrayList<>(Arrays.asList(
       new Car("Toyota","Yaris",450, 0.20),
        new Car("Ford","Mondeo",800, 0.20),
        new Car("Fiat","Multipla",600, 0.20),
        new Car("Citroen","C5",700, 0.20),
        new Car("Mercedes","C-Klasse",1200, 0.20),
        new Car("BMX","5",1200, 0.20)
    ));

    List<Member> members = new ArrayList<>(Arrays.asList(
       new Member("Hansi","h@ansi.dk", "123456", "Hans", "Zimmer", "Hansi Street", "Hanstown", 1000, true, 4),
       new Member("Sven","s@vend.dk", "654321", "Sven", "Sved", "Svengade", "Svendborg", 4400, true, 2),
       new Member("Karsten","k@arsten.dk", "666666", "Karsten", "Jensen", "Jensen vej", "København", 1205, false, 7),
       new Member("Karen","k@aren.dk", "696969", "Karen", "Malkeko", "Disney Hovedgade", "Disney", 9999, false, 10)

    ));

    carRepository.saveAll(cars);
    memberRepository.saveAll(members);
  }
}
