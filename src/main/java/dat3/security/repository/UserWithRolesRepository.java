package dat3.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import dat3.security.entity.UserWithRoles;

public interface
UserWithRolesRepository extends JpaRepository<UserWithRoles,String> {
    Boolean existsByEmail(String email);
}
