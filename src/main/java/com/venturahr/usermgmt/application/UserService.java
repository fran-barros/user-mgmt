package com.venturahr.usermgmt.application;

import com.venturahr.usermgmt.domain.User;
import com.venturahr.usermgmt.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(String cpf, User user) {
        Optional<User> userOptional = userRepository.findById(cpf);

        if(userOptional.isPresent()){
            User c = userOptional.get();
            c.setName(user.getName());
            c.setEmail(user.getEmail());
            c.setBirthdate(user.getBirthdate());
            c.setContacts(user.getContacts());
            c.setAddress(user.getAddress());
            return userRepository.save(c);
        }
        return null;
    }

    public Optional<User> getUser(String cpf, String email) {
        Specification<User> userSearch = User.builder()
                                                        .cpf(cpf)
                                                        .email(email)
                                                        .build()
                                                        .toSpec();
        return userRepository.findOne(userSearch);
    }

    public List<User> getUserByTelefone(String telefone) {
        return userRepository.buscaPorTelefone(telefone);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
