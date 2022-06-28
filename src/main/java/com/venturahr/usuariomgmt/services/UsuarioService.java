package com.venturahr.usuariomgmt.services;

import com.venturahr.usuariomgmt.domain.Usuario;
import com.venturahr.usuariomgmt.infraestructure.utils.UserIdValidator;
import com.venturahr.usuariomgmt.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(String identificador, Usuario usuario) {
        Optional<Usuario> usuarioBuscado;
        if(UserIdValidator.isValidCpfFormat(identificador)){
            usuarioBuscado = usuarioRepository.findByCpf(identificador);
        }
       else {
            usuarioBuscado = usuarioRepository.findByCnpj(identificador);
       }

        if(usuarioBuscado.isPresent()){
            Usuario c = usuarioBuscado.get();
            c.setNome(usuario.getNome());
            c.setEmail(usuario.getEmail());
            c.setDataNascimento(usuario.getDataNascimento());
            c.setContatos(usuario.getContatos());
            c.setEndereco(usuario.getEndereco());
            return usuarioRepository.save(c);
        }
        return null;
    }

    public Optional<Usuario> getUsuario(String identificador) {
        Specification<Usuario> usuarioSearch;
        if(identificador.contains("@")) {
            usuarioSearch = Usuario.builder()
                    .email(identificador)
                    .build()
                    .toSpec();
        }
        else{
            usuarioSearch = Usuario.builder()
                    .cpf(identificador)
                    .build()
                    .toSpec();
        }
        return usuarioRepository.findOne(usuarioSearch);
    }


    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }
}
