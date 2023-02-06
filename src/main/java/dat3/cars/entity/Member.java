package dat3.cars.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Lombok
@Getter
@Setter
@NoArgsConstructor

//Jakarta
@Entity
public class Member {

  @Id
  @Column(name = "username")
  private String username;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "password")
  private String password;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "last_name", nullable = false)
  private String lastName;
  @ElementCollection
  private List<String> favoriteCarColors = new ArrayList<>();

  @ElementCollection
  @MapKeyColumn(name = "description")
  @Column(name = "phoneNumber")
  Map<String,String> phones = new HashMap<>();
  @Column(name = "street", nullable = false)
  private String street;
  @Column(name = "city", nullable = false)
  private String city;
  @Column(name = "zip", nullable = false)
  private String zip;

  @Column(name = "approved")
  private boolean approved;

  @Column(name = "ranking")
  private int ranking;
  @CreationTimestamp
  private LocalDateTime created;
  @UpdateTimestamp
  private LocalDateTime lastEdited;


  public Member(String user, String password, String email,
                String firstName, String lastName, String street, String city, String zip) {
    this.username = user;
    this.password= password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.street = street;
    this.city = city;
    this.zip = zip;
  }

}
