package com.digital.customermgmt.domain;

import com.digital.customermgmt.infraestructure.validation.Telephone.Telephone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name ="contact")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    private String telephone;
}
