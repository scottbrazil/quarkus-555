package org.acme.project.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EstoqueBaixoConsumer {

    private static final Logger LOG = Logger.getLogger(EstoqueBaixoConsumer.class);

    @Incoming("estoque-baixo-in")
    public void consumir(String nomeProduto) {
        LOG.warnf("[ALERTA ESTOQUE] %s", nomeProduto);
    }
}