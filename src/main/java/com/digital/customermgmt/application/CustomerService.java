package com.digital.customermgmt.application;

import com.digital.customermgmt.domain.Customer;
import com.digital.customermgmt.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(String cpf, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findById(cpf);

        if(customerOptional.isPresent()){
            Customer c = customerOptional.get();
            c.setName(customer.getName());
            c.setEmail(customer.getEmail());
            c.setBirthdate(customer.getBirthdate());
            c.setContacts(customer.getContacts());
            c.setAddress(customer.getAddress());
            return customerRepository.save(c);
        }
        return null;
    }

    public Optional<Customer> getCustomer(String cpf, String email) {
        Specification<Customer> customerSearch = Customer.builder()
                                                        .cpf(cpf)
                                                        .email(email)
                                                        .build()
                                                        .toSpec();
        return customerRepository.findOne(customerSearch);
    }

    public List<Customer> getCustomerByTelefone(String telefone) {
        return customerRepository.buscaPorTelefone(telefone);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
