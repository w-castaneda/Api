package com.rest.service.Api.dto;

import com.rest.service.Api.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UsuarioDTO {

    @NotBlank
    private String nombre;

    @Email
    private String email;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNombre(this.nombre);
        usuario.setEmail(this.email);

        return usuario;
    }

    public static UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());

        return usuarioDTO;
    }
}
