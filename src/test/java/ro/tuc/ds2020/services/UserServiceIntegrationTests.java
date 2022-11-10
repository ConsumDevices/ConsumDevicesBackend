package ro.tuc.ds2020.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ro.tuc.ds2020.Ds2020TestConfig;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.util.List;
import java.util.UUID;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/test-sql/create.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/test-sql/delete.sql")
public class UserServiceIntegrationTests extends Ds2020TestConfig {

    @Autowired
    UserService userService;

    @Test
    public void testGetCorrect() {
        List<UserDTO> userDTOList = userService.findUsers();
        assertEquals("Test Insert User", 1, userDTOList.size());
    }

    @Test
    public void testInsertCorrectWithGetById() {
        //insereaza o persoana si ii luam id-ul
        UserDetailsDTO u = new UserDetailsDTO("John", "Somewhere Else street", 22, "email", "parola", "role");
        UUID insertedID = userService.insert(u);

        //vrem ca ce am inserat sa il gasim in BD si sa fie egale
        UserDetailsDTO insertedUser = new UserDetailsDTO(insertedID, u.getName(),u.getAddress(), u.getAge(), u.getEmail(), u.getPassword(), u.getRole());
        UserDetailsDTO fetchedUser = userService.findUserById(insertedID);

        assertEquals("Test Inserted Person", insertedUser, fetchedUser);
    }

    @Test
    public void testInsertCorrectWithGetAll() {
        UserDetailsDTO u = new UserDetailsDTO("John", "Somewhere Else street", 22, "email", "parola", "role");
        userService.insert(u); //il inseram

        //era o persoana, acum sunt 2 deci vrem sa verificam asta
        List<UserDTO> userDTOList = userService.findUsers();
        assertEquals("Test Inserted Users", 2, userDTOList.size());
    }
}
