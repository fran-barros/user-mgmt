package com.digital.customermgmt.domain;

import com.digital.customermgmt.infraestructure.validation.BirthDate.BirthDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    @NotEmpty
    private String cpf;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @Valid
    @BirthDate
    @NotEmpty
    private String birthdate;

    @Valid
    @NotEmpty
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cpf")
    private List<Contact> contacts;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER ,cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    public Specification<Customer> toSpec(){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(cpf)){
                predicates.add(
                        criteriaBuilder.equal(root.get("cpf"), cpf));
            }
            if (StringUtils.hasText(email)){
                predicates.add(
                        criteriaBuilder.equal(root.get("email"), email));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
