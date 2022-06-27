package com.digital.customermgmt.interfaces.rest;

import com.digital.customermgmt.application.CustomerService;
import com.digital.customermgmt.domain.Customer;
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
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService service;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> create(@RequestBody @Valid Customer customer){
        Customer customerResponse = service.create(customer);
        if(customerResponse == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> update(@RequestHeader("cpf") String cpf,
                                           @RequestBody @Valid Customer customer){
        Customer customerResponse = service.update(cpf , customer);
        if(customerResponse == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@RequestHeader(value = "cpf", required = false) String cpf,
                                                    @RequestHeader(value = "email", required = false) String email){
        if(cpf == null && email == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Customer> customer = service.getCustomer(cpf, email);

        if(customer.isPresent()){
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = {"/telefone"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getCustomerByTelefone(@RequestHeader(value = "telefone") String telefone){

        List<Customer> customer = service.getCustomerByTelefone(telefone);

        if(!customer.isEmpty()){
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = {"/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAll(){

        List<Customer> customers = service.getAll();

        if(!customers.isEmpty()){
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
