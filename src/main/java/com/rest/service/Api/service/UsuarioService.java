package com.rest.service.Api.service;

import com.rest.service.Api.model.Usuario;
import com.rest.service.Api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepository;


    public UsuarioService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Usuario> getAllUsuarios() {
        return userRepository.findAll();
    }

    public Usuario getUsuarioById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return userRepository.save(usuario);
    }

    public List<Usuario> savedUsuarios(List<Usuario> usuarios) {
        return userRepository.saveAll(usuarios);
    }

    public void deleteUsuario(Long id) {
        userRepository.deleteById(id);
    }
}
