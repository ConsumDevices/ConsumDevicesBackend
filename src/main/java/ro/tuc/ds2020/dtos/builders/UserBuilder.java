package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.entities.User;

public class UserBuilder {

    private UserBuilder() {
    }

    // din entity Person in PersonDTO, cele 3 campuri fara adresa
    // e static
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getAddress(), user.getEmail(), user.getRole());
    }

    // din Person in PersonDetailsDTO
    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(user.getId(), user.getName(), user.getAddress(), user.getAge(), user.getEmail(), user.getPassword(), user.getRole());
    }

    // din details in entity, in Person
    // fara uuid
    public static User toUserEntity(UserDetailsDTO userDetailsDTO) {
        return new User(userDetailsDTO.getName(),
                userDetailsDTO.getAddress(),
                userDetailsDTO.getAge(),
                userDetailsDTO.getEmail(),
                userDetailsDTO.getPassword(),
                userDetailsDTO.getRole());
    }
}
