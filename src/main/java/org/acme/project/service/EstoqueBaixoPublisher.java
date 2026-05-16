package org.acme.project.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class EstoqueBaixoPublisher {

    @Inject
    @Channel("estoque-baixo-out")
    Emitter<String> emitter;

    public void publicarAlerta(String nomeProduto) {
        emitter.send(nomeProduto);
    }
}