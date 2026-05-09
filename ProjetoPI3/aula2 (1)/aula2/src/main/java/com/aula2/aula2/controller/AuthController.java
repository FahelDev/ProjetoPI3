package com.aula2.aula2.controller;

import com.aula2.aula2.model.Usuario;
import com.aula2.aula2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite que o seu HTML acesse a API sem erros de CORS
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ROTA DE CADASTRO
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Este e-mail já está cadastrado!");
        }
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    // ROTA DE LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginDados) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(loginDados.getEmail());

        if (usuario.isPresent() && usuario.get().getSenha().equals(loginDados.getSenha())) {
            return ResponseEntity.ok(usuario.get()); // Retorna o usuário logado
        }
        
        return ResponseEntity.status(401).body("E-mail ou senha incorretos!");
    }
}