package com.venturahr.usermgmt.interfaces.rest;

import com.venturahr.usermgmt.services.UserService;
import com.venturahr.usermgmt.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody @Valid User user){
        User userResponse = service.create(user);
        if(userResponse == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@RequestHeader("cpf") String cpf,
                                           @RequestBody @Valid User user){
        User userResponse = service.update(cpf , user);
        if(userResponse == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@RequestHeader(value = "cpf", required = false) String cpf,
                                                    @RequestHeader(value = "email", required = false) String email){
        if(cpf == null && email == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<User> user = service.getUser(cpf, email);

        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = {"/telefone"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUserByTelefone(@RequestHeader(value = "telefone") String telefone){

        List<User> user = service.getUserByTelefone(telefone);

        if(!user.isEmpty()){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = {"/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAll(){

        List<User> users = service.getAll();

        if(!users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
