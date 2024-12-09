package org.todolist.todolistapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.todolist.todolistapi.model.User;
import org.todolist.todolistapi.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Registro de nuevo usuario y contraseña
    public User registerUser(String username, String password){
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("El usuario ya existe");
        }
        User user = new User(username, password);
        return userRepository.save(user);
    }

    //Buscar usuario por nombre de usuario
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
