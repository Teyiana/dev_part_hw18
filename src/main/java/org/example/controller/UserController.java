package org.example.controller;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @GetMapping(value = "/readAll")
    @ResponseBody
    public List<UserDTO> read() {
        final List<UserDTO> users = userService.findAllUsers();
        return users;
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<UserDTO> read(@PathVariable(name = "id") long id) {
        final UserDTO user = userService.findUserById(id);

        return user != null
               ? new ResponseEntity<>(user, HttpStatus.OK)
               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/edit/")
    @ResponseBody
    public UserDTO update( @RequestBody UserDTO user) {
       return userService.update(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
          userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
