package org.acme.project.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.project.model.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    public Usuario findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
