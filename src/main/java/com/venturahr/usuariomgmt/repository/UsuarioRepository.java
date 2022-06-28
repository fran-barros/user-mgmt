package com.venturahr.usuariomgmt.repository;

import com.venturahr.usuariomgmt.domain.Usuario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findOne(Specification<Usuario> toSpec);

    Optional<Usuario> findByCpf(String identificador);

    Optional<Usuario> findByCnpj(String identificador);

    List<Usuario> findByEmail(String email);
}
