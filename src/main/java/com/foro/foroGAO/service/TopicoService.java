package com.foro.foroGAO.service;

import com.foro.foroGAO.model.Topico;
import com.foro.foroGAO.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;

    public TopicoService(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    public Topico registrarTopico(Topico topico) {
        // Verificar si existe un tópico con el mismo título y mensaje
        Optional<Topico> existente = topicoRepository.findByTituloAndMensaje(topico.getTitulo(), topico.getMensaje());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje.");
        }

        // Guardar el tópico en la base de datos
        return topicoRepository.save(topico);
    }
}



