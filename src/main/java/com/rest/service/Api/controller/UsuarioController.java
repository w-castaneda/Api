package com.rest.service.Api.controller;

import com.rest.service.Api.dto.UsuarioDTO;
import com.rest.service.Api.model.Usuario;
import com.rest.service.Api.service.UsuarioService;
import io.micrometer.core.instrument.Counter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/usuarios")
@Tag(name = "Usuario", description = "Controller for managing users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    // Contador para el endpoint de obtener usuario por ID
    private final Counter customEndpointCounter;

    public UsuarioController(UsuarioService usuarioService, Counter usuarioGetCounter, Counter customEndpointCounter) {
        this.usuarioService = usuarioService;
        this.customEndpointCounter = customEndpointCounter;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users saved in the database")
    @ApiResponse(responseCode = "200", description = "List of users" )
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        logger.info("Fetching all users");
        List<Usuario> usuario = usuarioService.getAllUsuarios();
        List<UsuarioDTO> usuarioDTO = usuario.stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Returns a user by its id")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public UsuarioDTO getUsuarioById(@PathVariable final  Long id) {
        logger.info("Fetching user with id: {}", id);

        // Incrementa el contador cada vez que se consulta este endpoint
        customEndpointCounter.increment();
        logger.info("Incrementando contador de usuarios");

        try {
            Thread.sleep(2000);
            Usuario usuario = usuarioService.getUsuarioById(id);
            if (usuario == null) {
                logger.error("User with id {} not found", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
            }
            UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);
            logger.debug("Usuario encontrado: {}", usuarioDTO);
            return usuarioDTO;
        } catch (InterruptedException e) {
            logger.error("Error while sleeping thread", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }

    }

    @PostMapping
    @Operation(summary = "create user", description = "Creates a new user")
    @ApiResponse(responseCode = "201", description = "user created")
    public ResponseEntity<UsuarioDTO> saveUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
         Usuario usuario = usuarioService.saveUsuario(usuarioDTO.toEntity());
         UsuarioDTO usuarioSavedDTO = UsuarioDTO.fromEntity(usuario);
        return ResponseEntity.status(201).body(usuarioSavedDTO);
    }

    @PostMapping("/batch")
    @Operation(summary = "create users", description = "Creates a list of new users")
    @ApiResponse(responseCode = "201", description = "user created")
    public ResponseEntity<List<UsuarioDTO>> savedUsuarios(@RequestBody @Valid List<UsuarioDTO> usuariosDTO) {

        logger.info("Saving list of users");
        // Guardamos la lista de usuarios
        List<Usuario> savedUsuarios = usuariosDTO.stream()
                .map(UsuarioDTO::toEntity)
                .collect(Collectors.toList());

        logger .info("Saving users to database");
        // Convertimos las entidades guardadas de vuelta a DTOs
        List<UsuarioDTO> usuariosSavedDTOs = savedUsuarios.stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    logger .info("Users saved successfully");
        return ResponseEntity.status(201).body(usuariosSavedDTOs);
    }
}
