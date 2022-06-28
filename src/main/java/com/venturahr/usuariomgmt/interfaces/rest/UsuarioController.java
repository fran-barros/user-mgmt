package com.venturahr.usuariomgmt.interfaces.rest;

import com.venturahr.usuariomgmt.domain.Usuario;
import com.venturahr.usuariomgmt.services.UsuarioService;
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
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario usuario){
        Usuario usuarioResponse = service.create(usuario);
        if(usuarioResponse == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> update(@RequestHeader("identificador") String identificador,
                                          @RequestBody @Valid Usuario usuario){
        Usuario usuarioResponse = service.update(identificador , usuario);
        if(usuarioResponse == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuarioResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuario(@RequestHeader(value = "identificador") String identificador){
        if(identificador == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Usuario> usuario = service.getUsuario(identificador);

        if(usuario.isPresent()){
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = {"/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> getAll(){

        List<Usuario> usuarios = service.getAll();

        if(!usuarios.isEmpty()){
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
