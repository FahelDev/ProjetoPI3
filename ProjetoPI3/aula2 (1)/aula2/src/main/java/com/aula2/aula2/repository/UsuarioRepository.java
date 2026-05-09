package com.aula2.aula2.repository;

import com.aula2.aula2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Esta interface é a "ponte" que salva o usuário no banco de dados
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método para buscar o usuário pelo e-mail (usaremos no Login)
    Optional<Usuario> findByEmail(String email);
}