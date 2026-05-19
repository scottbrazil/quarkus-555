package org.acme.project.resource;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;

import org.acme.project.dto.UsuarioDTO;
import org.acme.project.dto.UsuarioLoginDTO;
import org.acme.project.mapper.UsuarioMapper;
import org.acme.project.model.Usuario;
import org.acme.project.repository.UsuarioRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioRepository usuarioRepository;

    @POST
    @Transactional
    @Path("/register")
    public Response register(@Valid UsuarioDTO dto) {
        try {
            Usuario entity = UsuarioMapper.toEntity(dto);
            usuarioRepository.persist(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(UsuarioMapper.toDTO(entity))
                    .build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro","Campos obrigatórios não informados"))
                    .build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("erro", "E-mail já cadastrado"))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@Valid UsuarioLoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());
        if (usuario != null && BcryptUtil.matches(dto.getSenha(), usuario.getSenha())) {
            String token = Jwt.issuer("quarkus-avancado")
                    .upn(usuario.getEmail())
                    .groups(new HashSet<>(usuario.getRole()))
                    .subject(usuario.getNome())
                    .issuedAt(java.time.Instant.now())
                    .expiresIn(Duration.ofHours(1))
                    .sign();
            return Response.ok(Map.of("token", token, "type", "Bearer", "roles", usuario.getRole())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("erro","E-mail ou senha inválidos"))
                    .build();
        }
    }
}
