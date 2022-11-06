package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//interfata, in service implementam
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Example: JPA generate Query by Field
     */
    //direct din denumire, si asa stie pentru ca e byName sa caute dupa nume
    List<User> findByName(String name);

    //List<User> findByAge(int age);

    List<User> findByEmail(String email);

    //List<User> findByAddress(String address);

    /**
     * Example: Write Custom Query
     */
    @Query(value = "SELECT u " +
            "FROM User u " +
            "WHERE u.name = :name " +
            "AND u.age >= 60  ")
    Optional<User> findSeniorsByName(@Param("name") String name);
    //ii dai numele pentru person, il returneaza

}
