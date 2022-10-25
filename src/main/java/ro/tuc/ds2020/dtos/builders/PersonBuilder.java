package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.entities.Person;

public class PersonBuilder {

    private PersonBuilder() {
    }

    // din entity Person in PersonDTO, cele 3 campuri fara adresa
    // e static
    public static PersonDTO toPersonDTO(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getAge());
    }

    // din Person in PersonDetailsDTO
    public static PersonDetailsDTO toPersonDetailsDTO(Person person) {
        return new PersonDetailsDTO(person.getId(), person.getName(), person.getAddress(), person.getAge());
    }

    // din details in entity, in Person
    // fara uuid
    public static Person toEntity(PersonDetailsDTO personDetailsDTO) {
        return new Person(personDetailsDTO.getName(),
                personDetailsDTO.getAddress(),
                personDetailsDTO.getAge());
    }
}
