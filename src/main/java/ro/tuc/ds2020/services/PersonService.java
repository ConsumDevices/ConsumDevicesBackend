package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.dtos.builders.PersonBuilder;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findPersons() {
        List<Person> personList = personRepository.findAll();
        return personList.stream()
                .map(PersonBuilder::toPersonDTO) //avem o lista de persoane, transformam in DTOs
                .collect(Collectors.toList()); //schimbam din stream in lista
    }

    public PersonDetailsDTO findPersonById(UUID id) {
        //optional pentru ca poate e null, poate il gaseste
        Optional<Person> prosumerOptional = personRepository.findById(id);
        //isPresent predefinita
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            //apeleaza o exceptie din pachetul de exceptii
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
        return PersonBuilder.toPersonDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(PersonDetailsDTO personDTO) {
        //din dto in entity
        Person person = PersonBuilder.toEntity(personDTO);
        //save e update sau insert
        person = personRepository.save(person);
        LOGGER.debug("Person with id {} was inserted in db", person.getId());
        return person.getId();
    }

}
