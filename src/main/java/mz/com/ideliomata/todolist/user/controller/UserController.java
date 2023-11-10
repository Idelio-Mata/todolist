package mz.com.ideliomata.todolist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import mz.com.ideliomata.todolist.user.model.UserModel;
import mz.com.ideliomata.todolist.user.repository.InterfaceUserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private InterfaceUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        
        if (user != null) {
            //Mensagem de erro
            //Status Code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário existente");
        }

        var passwordHashred = BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

}
