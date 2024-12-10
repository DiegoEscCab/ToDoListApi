package org.todolist.todolistapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todolist.todolistapi.model.User;
import org.todolist.todolistapi.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Obtener usuario by username
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
