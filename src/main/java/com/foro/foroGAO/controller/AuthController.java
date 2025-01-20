package com.foro.foroGAO.controller;

import com.foro.foroGAO.dto.LoginRequest;
import com.foro.foroGAO.dto.TokenResponse;
import com.foro.foroGAO.model.Usuario;
import com.foro.foroGAO.repository.UsuarioRepository;
import com.foro.foroGAO.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inyectamos PasswordEncoder

    @Autowired
    private UsuarioRepository usuarioRepository; // Inyectamos el repositorio para acceder a los usuarios

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        // Buscamos el usuario por su correo (email)
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreoElectronico(loginRequest.getEmail());

        // Verificamos si el usuario existe y si la contraseña es correcta
        if (optionalUsuario.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), optionalUsuario.get().getContrasena())) {
            Usuario usuario = optionalUsuario.get();
            // Si el usuario existe y la contraseña es correcta, generamos el token
            String token = jwtTokenProvider.generateToken(usuario.getCorreoElectronico());
            return ResponseEntity.ok(new TokenResponse(token));  // Retornamos el token en la respuesta
        } else {
            return ResponseEntity.status(401).body(null);  // Si las credenciales son incorrectas
        }
    }
}
