package org.acme.project.mapper;

import java.util.Arrays;

import org.acme.project.dto.UsuarioDTO;
import org.acme.project.model.Usuario;

import io.quarkus.elytron.security.common.BcryptUtil;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(BcryptUtil.bcryptHash(usuario.getSenha()));
        dto.setRole(usuario.getRole());
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(BcryptUtil.bcryptHash(dto.getSenha()));
        usuario.setRole(dto.getRole() != null ? dto.getRole() : Arrays.asList("USER"));
        return usuario;
    }
}
