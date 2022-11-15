package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.dtos.UserLoginDTO;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private static final Gson gson = new Gson();
    public static UserDetailsDTO userLogat = new UserDetailsDTO();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        userLogat.setRole("nelogat");
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        //lista de persoane dto, apeleaza o functie din service
        List<UserDTO> dtos = userService.findUsers();
        for (UserDTO dto : dtos) {
            Link userLink = linkTo(methodOn(UserController.class)
                    .getUser(dto.getId())).withRel("userDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value="/role")
    public ResponseEntity<String> getRole() {
        //lista de persoane dto, apeleaza o functie din service
        String userRole = userLogat.getRole();
        //System.out.println("Rolul actual: " + userRole);
        return new ResponseEntity<>(gson.toJson(userRole), HttpStatus.OK);
    }

    /*
    @GetMapping(value="/id")
    public ResponseEntity<UUID> getId() {
        //lista de persoane dto, apeleaza o functie din service
        UUID userId = userLogat.getId();
        System.out.println("Id actual: " + userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
     */

    @GetMapping(value="/name")
    public ResponseEntity<String> getUserName() {
        //lista de persoane dto, apeleaza o functie din service
        String userName = userLogat.getName();
        //System.out.println("Rolul actual: " + userRole);
        return new ResponseEntity<>(gson.toJson(userName), HttpStatus.OK);
    }

    @GetMapping(value="/roleLogout")
    public ResponseEntity<String> getRoleLogout() {
        //lista de persoane dto, apeleaza o functie din service
        userLogat.setRole("neLogat");
        userLogat.setName("Nelogat");
        userLogat.setEmail("emailNelogat@email.com");
        userLogat.setPassword("pasNel1-");
        userLogat.setAddress("addressNelogat");
        userLogat.setAge(99);
        String userRole = userLogat.getRole();
        return new ResponseEntity<>(gson.toJson(userRole), HttpStatus.OK);
    }

    // primeste un personDTO, ii da insert, insert aparent ii returneaza UUID-ul
    @PostMapping()
    public ResponseEntity<UUID> insertUser(@Valid @RequestBody UserDetailsDTO userDTO) {
        UUID userID = userService.insert(userDTO);
        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @PostMapping(value="/update")
    public ResponseEntity<UUID> updateUser(@Valid @RequestBody UserDetailsDTO userDTO) {

        UserDetailsDTO userDetailsDTO = userService.findByName(userDTO.getName());
        userDetailsDTO.setAge(userDTO.getAge());
        userDetailsDTO.setAddress(userDTO.getAddress());
        userDetailsDTO.setPassword(userDTO.getPassword());
        userDetailsDTO.setRole(userDTO.getRole());
        userDetailsDTO.setEmail(userDTO.getEmail());

        //System.out.println("Controller " + userDetailsDTO.getId());

        UUID userID = userService.update(userDetailsDTO);
        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @PostMapping(value="/delete")
    public ResponseEntity<UUID> deleteUser(@Valid @RequestBody UserLoginDTO userDTO) {

        UserDetailsDTO userDetailsDTO = userService.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());

        //System.out.println("Controller " + userDetailsDTO.getId());

        UUID userID = userService.delete(userDetailsDTO.getId());
        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @PostMapping(value="/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userDTO) {
        UserDetailsDTO userDetailsDTO = userService.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        String userRole = userDetailsDTO.getRole();
        System.out.println(userRole);

        userLogat = userDetailsDTO;
        return new ResponseEntity<>(gson.toJson(userRole), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetailsDTO> getUser(@PathVariable("id") UUID userId) {
        UserDetailsDTO dto = userService.findUserById(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



    //TODO: UPDATE, DELETE per resource

}
