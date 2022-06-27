package com.venturahr.usermgmt.repository;

import com.venturahr.usermgmt.domain.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> , JpaSpecificationExecutor<User> {

    @Query("SELECT (c) FROM User c WHERE (c.name) like %:name% ")
    List<User> findByName(@Param("name") String name);

    Optional<User> findOne(Specification<User> toSpec);

    @Query(value = "SELECT DISTINCT * FROM User cs INNER JOIN Contact ctt ON cs.cpf = ctt.cpf WHERE ctt.telephone = :telephone ", nativeQuery = true)
    List<User> buscaPorTelefone(@Param("telephone") String telephone);
}
