package com.example.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepo;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="http://localhost:5173")
public class AuthController {
    @Autowired
    private final UserRepo userRepo;
    public AuthController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loging(@RequestBody User loginuser){
        Optional<User> user = userRepo.findByEmail(loginuser.getEmail());
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("Email not registered");
        }
        if(!user.get().getPassword().equals(loginuser.getPassword())){
            return ResponseEntity.badRequest().body("Incorrect password or mail ");
        }
        return ResponseEntity.ok("Login Successfull! Welcome back");
    } 
    
}
