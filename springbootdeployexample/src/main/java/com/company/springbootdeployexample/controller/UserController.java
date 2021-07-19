package com.company.springbootdeployexample.controller;

import com.company.springbootdeployexample.exception.resourceNotFoundException;
import com.company.springbootdeployexample.model.User;
import com.company.springbootdeployexample.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    //get

    @Autowired
    private UserRepo userRepo;
    private User user;

    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepo.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long userId){
        return this.userRepo.findById(userId).orElseThrow(() -> new resourceNotFoundException("User not found with id :" + userId));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<User>(userRepo.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(value = "id") long userid, @RequestBody User user){
        User existing = this.userRepo.findById(userid).orElseThrow(() -> new resourceNotFoundException("User not found with id :" + userid));
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
       return this.userRepo.save(existing);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id){
        User existing = this.userRepo.findById(id).orElseThrow(() -> new resourceNotFoundException("User not found with id :" + id));
         this.userRepo.deleteById(id);
         return ResponseEntity.ok().build();

    }
}
