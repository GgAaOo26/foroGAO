package com.foro.foroGAO.controller;

import com.foro.foroGAO.dto.TopicoRequest;
import com.foro.foroGAO.dto.TopicoResponse;
import com.foro.foroGAO.model.Curso;
import com.foro.foroGAO.model.Topico;
import com.foro.foroGAO.model.Usuario;
import com.foro.foroGAO.repository.CursoRepository;
import com.foro.foroGAO.repository.TopicoRepository;
import com.foro.foroGAO.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // Registrar un nuevo tópico
    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid TopicoRequest topicoRequest) {
        if (topicoRepository.existsByTituloAndMensaje(topicoRequest.getTitulo(), topicoRequest.getMensaje())) {
            return ResponseEntity.badRequest().body("Tópico duplicado: ya existe uno con el mismo título y mensaje.");
        }

        Usuario autor = usuarioRepository.findById(topicoRequest.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado."));
        Curso curso = cursoRepository.findById(topicoRequest.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado."));

        Topico topico = new Topico();
        topico.setTitulo(topicoRequest.getTitulo());
        topico.setMensaje(topicoRequest.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);

        return ResponseEntity.ok("Tópico registrado con éxito.");
    }

    // Obtener todos los tópicos
    @GetMapping
    public ResponseEntity<List<TopicoResponse>> obtenerTodosLosTopicos() {
        List<TopicoResponse> topicos = topicoRepository.findAll().stream()
                .map(this::convertirATopicoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicos);
    }

    // Obtener un tópico por ID
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> obtenerTopicoPorId(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado."));
        return ResponseEntity.ok(convertirATopicoResponse(topico));
    }

    // Actualizar un tópico por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoRequest topicoRequest) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado."));

        // Verificar si los datos del tópico son válidos antes de actualizarlos
        if (topicoRepository.existsByTituloAndMensaje(topicoRequest.getTitulo(), topicoRequest.getMensaje())) {
            return ResponseEntity.badRequest().body("Tópico duplicado: ya existe uno con el mismo título y mensaje.");
        }

        // Actualizar los campos del tópico
        topico.setTitulo(topicoRequest.getTitulo());
        topico.setMensaje(topicoRequest.getMensaje());

        // Guardar el tópico actualizado
        topicoRepository.save(topico);

        // Convertir el tópico actualizado en un objeto TopicoResponse
        TopicoResponse topicoResponse = convertirATopicoResponse(topico);
        return ResponseEntity.ok(topicoResponse);
    }

    // Eliminar un tópico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        // Verificar si el tópico existe
        if (!topicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado.");
        }

        // Eliminar el tópico
        topicoRepository.deleteById(id);

        // Retornar respuesta de éxito
        return ResponseEntity.noContent().build();  // 204 No Content
    }



    // Método privado para convertir un Topico a TopicoResponse
    private TopicoResponse convertirATopicoResponse(Topico topico) {
        return new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}

