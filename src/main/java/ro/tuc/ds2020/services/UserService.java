package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO findUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDetailsDTO(userOptional.get());
    }

    public UUID insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toUserEntity(userDTO);
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public UserDetailsDTO findByName(String name) {
        Optional<User> userOptional = userRepository.findByName(name);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with name {} was not found in db", name);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with name: " + name);
        }
        return UserBuilder.toUserDetailsDTO(userOptional.get());
    }

    public UUID update(UserDetailsDTO userDTO) {
        User user = UserBuilder.toUserEntityUpdate(userDTO);
        System.out.println(user.getId());
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was update in db", user.getId());
        return user.getId();
    }

    public UUID delete(UUID userId) {
        userRepository.deleteById(userId);
        LOGGER.debug("User with id {} was deleted from db", userId);
        return userId;
    }



    public UserDetailsDTO findByEmailAndPassword(String email, String password)
    {
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with email {} and password {} was not found in db", email, password);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with email: " + email + " and password " + password);
        }
        return UserBuilder.toUserDetailsDTO(userOptional.get());
    }

}
