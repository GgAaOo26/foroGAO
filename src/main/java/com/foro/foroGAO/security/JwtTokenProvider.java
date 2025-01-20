package com.foro.foroGAO.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "mi_clave_secreta_para_firmar_el_token"; // Usamos HmacSHA256

    // Método para generar el token JWT usando HmacSHA256
    public String generateToken(String username) {
        // Algoritmo de firma HS256 con la clave secreta
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        // Se establece el token con un asunto (el correo del usuario), la fecha de emisión y de expiración
        return JWT.create()
                .withSubject(username) // Establecer el nombre de usuario (o correo)
                .withIssuedAt(new Date()) // Fecha de emisión
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // Expira en 24 horas
                .sign(algorithm); // Firmar con la clave secreta y el algoritmo
    }

    // Método para validar el token JWT
    public boolean validateToken(String token) {
        try {
            // El token debe ser decodificado y validado con el mismo algoritmo y la clave secreta
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // Usamos HmacSHA256
            JWT.require(algorithm)
                    .build()
                    .verify(token); // Verifica que el token sea válido
            return true;
        } catch (Exception e) {
            // Si ocurre cualquier excepción, el token es inválido
            System.out.println("Token inválido o expirado: " + e.getMessage());
            return false;
        }
    }

    // Método para extraer el nombre de usuario (o cualquier otro dato) del token
    public String getUsernameFromToken(String token) {
        // Decodifica el token y extrae el asunto (el nombre de usuario)
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }
}

