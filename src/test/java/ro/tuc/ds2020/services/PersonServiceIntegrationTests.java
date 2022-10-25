package ro.tuc.ds2020.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ro.tuc.ds2020.Ds2020TestConfig;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.util.List;
import java.util.UUID;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/test-sql/create.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/test-sql/delete.sql")
public class PersonServiceIntegrationTests extends Ds2020TestConfig {

    @Autowired
    PersonService personService;

    @Test
    public void testGetCorrect() {
        List<PersonDTO> personDTOList = personService.findPersons();
        assertEquals("Test Insert Person", 1, personDTOList.size());
    }

    @Test
    public void testInsertCorrectWithGetById() {
        //insereaza o persoana si ii luam id-ul
        PersonDetailsDTO p = new PersonDetailsDTO("John", "Somewhere Else street", 22);
        UUID insertedID = personService.insert(p);

        //vrem ca ce am inserat sa il gasim in BD si sa fie egale
        PersonDetailsDTO insertedPerson = new PersonDetailsDTO(insertedID, p.getName(),p.getAddress(), p.getAge());
        PersonDetailsDTO fetchedPerson = personService.findPersonById(insertedID);

        assertEquals("Test Inserted Person", insertedPerson, fetchedPerson);
    }

    @Test
    public void testInsertCorrectWithGetAll() {
        PersonDetailsDTO p = new PersonDetailsDTO("John", "Somewhere Else street", 22);
        personService.insert(p); //il inseram

        //era o persoana, acum sunt 2 deci vrem sa verificam asta
        List<PersonDTO> personDTOList = personService.findPersons();
        assertEquals("Test Inserted Persons", 2, personDTOList.size());
    }
}
