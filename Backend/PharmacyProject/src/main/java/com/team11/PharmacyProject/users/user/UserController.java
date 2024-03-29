package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.user.*;
import com.team11.PharmacyProject.exceptions.CustomException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        try {
            userService.insertUser(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        } catch (CustomException ce) {
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserCrudDTO>> getUsers(@RequestParam String type) {
        List<UserCrudDTO> users = userService.getUsersByUserType(type).stream().map(this::convertToCrudDto).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileInfoDTO> getUser(@PathVariable("id") Long id) {
        MyUser user = userService.findOne(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.map(user, UserProfileInfoDTO.class), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('PATIENT', 'PHARMACY_ADMIN', 'SUPPLIER', 'ADMIN', 'DERMATOLOGIST', 'PHARMACIST')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDTO user, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>("Bad request!", HttpStatus.BAD_REQUEST);
        }

        try {
            MyUser updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(mapper.map(updatedUser, UserUpdateDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/change-password/{id}")
    @PreAuthorize("hasAnyAuthority('PATIENT', 'PHARMACY_ADMIN', 'SUPPLIER', 'ADMIN', 'DERMATOLOGIST', 'PHARMACIST')")
    public ResponseEntity<String> changePassword(@PathVariable("id") Long userId, @RequestBody ChangePasswordDTO changePassword) {
        String oldPassword = changePassword.getOldPassword();
        String newPassword = changePassword.getNewPassword();
        try {
            userService.changePassword(userId, oldPassword, newPassword);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error. Password not changed.", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = "/set-password/{id}")
    @PreAuthorize("hasAnyAuthority('PATIENT', 'PHARMACY_ADMIN', 'SUPPLIER', 'ADMIN', 'DERMATOLOGIST', 'PHARMACIST')")
    public ResponseEntity<String> setPassword(@PathVariable("id") Long userId, @RequestBody SetPasswordDTO setPassword) {
        String newPassword = setPassword.getNewPassword();
        if (userService.setPassword(userId, newPassword)) {
            return new ResponseEntity<>("Password set successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Password not set.", HttpStatus.FORBIDDEN);
        }
    }

    private UserCrudDTO convertToCrudDto(MyUser user) {
        return mapper.map(user, UserCrudDTO.class);
    }

    private MyUser convertToEntity(UserDTO userDto) {
        return mapper.map(userDto, MyUser.class);
    }
}
