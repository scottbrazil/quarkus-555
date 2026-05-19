package org.acme.project;

import java.util.Arrays;

import org.acme.project.model.Usuario;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StartupBean {
    @Inject
    EntityManager em;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        Log.info("*********** on Start *************");

        Usuario u = new Usuario();
        u.setNome("Admin Sistema");
        u.setEmail("admin@loja.com");
        u.setSenha(BcryptUtil.bcryptHash("admin123"));
        u.setRole(Arrays.asList("ADMIN"));
        em.persist(u);
        
        u = new Usuario();
        u.setNome("User padrão");
        u.setEmail("user@loja.com");
        u.setSenha(BcryptUtil.bcryptHash("user123"));
        u.setRole(Arrays.asList("USER"));
        em.persist(u);

    }
}
