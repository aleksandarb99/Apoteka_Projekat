package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.PharmacyDTO;
import com.team11.PharmacyProject.dto.UserCrudDTO;
import com.team11.PharmacyProject.dto.UserDTO;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDTO userDto) {
        MyUser user = convertToEntity(userDto);
        if (userService.insertUser(user)) {
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<List<UserCrudDTO>> getUsers(@RequestParam UserType type) {
        List<UserCrudDTO> users = userService.getUsersByUserType(type).stream().map(this::convertToCrudDto).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // TODO check database
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        if (userService.delete(id)) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getGreeting(@PathVariable("id") Long id) {
        MyUser user = userService.findOne(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO user, BindingResult result) throws Exception {

        if(result.hasErrors()) {
            return null;
        }

        MyUser updatedUser = userService.updateUser(user);

        if(updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(mapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    private UserCrudDTO convertToCrudDto(MyUser user) {
        return mapper.map(user, UserCrudDTO.class);
    }

    private MyUser convertCrudDtoToEntity(UserCrudDTO userCrudDto) {
        return mapper.map(userCrudDto, MyUser.class);
    }

    private UserDTO convertToDto(MyUser user) {
        return mapper.map(user, UserDTO.class);
    }

    private MyUser convertToEntity(UserDTO userDto) {
        return mapper.map(userDto, MyUser.class);
    }
}
