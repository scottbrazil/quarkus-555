package org.acme.project.mapper;

import org.acme.project.dto.UsuarioDTO;
import org.acme.project.enums.RoleEnum;
import org.acme.project.model.Usuario;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        dto.setRole(usuario.getRole() != null ? usuario.getRole() : null);
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
        usuario.setSenha(dto.getSenha());
        try {
            usuario.setRole(dto.getRole() != null ? RoleEnum.valueOf(dto.getRole().name()) : RoleEnum.USER);
        } catch (IllegalArgumentException e) {
            usuario.setRole(RoleEnum.USER);
        }
        return usuario;
    }
}
