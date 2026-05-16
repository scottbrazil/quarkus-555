package org.acme.project.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.project.model.Produto;

import java.util.List;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {


}
