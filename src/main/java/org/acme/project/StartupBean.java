package org.acme.project;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.acme.project.enums.RoleEnum;
import org.acme.project.model.Usuario;

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
        u.setSenha("admin123");
        u.setRole(RoleEnum.ADMIN);
        em.persist(u);

    }
}
