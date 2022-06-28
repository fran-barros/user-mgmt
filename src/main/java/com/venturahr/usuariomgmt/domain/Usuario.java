package com.venturahr.usuariomgmt.domain;

import com.venturahr.usuariomgmt.infraestructure.validation.BirthDate.BirthDate;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name ="usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @Email
    private String email;

    // dados pessoa jurídica
    private String cnpj;
    private String razaoSocial;
    //

    // dados pessoa física
    private String cpf;
    private String nome;
    @Valid
    @BirthDate
    private String dataNascimento;
    //

    @Valid
    @NotEmpty
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "cpf")
    private List<Contato> contatos;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER ,cascade=CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Endereco endereco;


    public Specification<Usuario> toSpec(){
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
