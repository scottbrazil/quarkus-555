package org.acme.project.resource;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import org.acme.project.model.Produto;
import org.acme.project.repository.ProdutoRepository;
import org.acme.project.service.EstoqueBaixoPublisher;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    static final String CACHE_NAME = "produtos";

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    EstoqueBaixoPublisher estoqueBaixoPublisher;

    private String getMessageEstoqueBaixo(Produto produto) {
        return String.format("Produto %s com estoque baixo (%d unidades)", produto.getNome(), produto.getEstoque());
    }

    @POST
    @Transactional
    @RolesAllowed("ADMIN")
    @CacheInvalidateAll(cacheName = CACHE_NAME)
    public Response salvarProduto(Produto produto) {
        try {
            produtoRepository.persist(produto);
            if (produto.getEstoque() < 5) {
                estoqueBaixoPublisher.publicarAlerta(getMessageEstoqueBaixo(produto));
            }
            return Response.ok(produto).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro","Campos obrigatórios não informados"))
                    .build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @CacheInvalidateAll(cacheName = CACHE_NAME)
    public Response atualizarProduto(@PathParam("id") Long id, Produto produto) {
        Produto produtoExistente = produtoRepository.findById(id);
        if (produtoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("erro", "Produto não encontrado")).build();
        }
        try {
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setDescricao(produto.getDescricao());
            produtoExistente.setPreco(produto.getPreco());
            produtoExistente.setEstoque(produto.getEstoque());
            produtoRepository.persist(produtoExistente);
            if (produtoExistente.getEstoque() < 5) {
                estoqueBaixoPublisher.publicarAlerta(getMessageEstoqueBaixo(produto));
            }
            return Response.ok(produtoExistente).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro","Campos obrigatórios não informados"))
                    .build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @CacheInvalidate(cacheName = CACHE_NAME)
    @CacheInvalidateAll(cacheName = CACHE_NAME)
    public Response deletarProduto(@PathParam("id") Long id) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("erro", "Produto não encontrado")).build();
        }
        produtoRepository.delete(produto);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @CacheResult(cacheName = CACHE_NAME)
    public List<Produto> listarProdutos() {
        return produtoRepository.listAll(Sort.by("id"));
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    @CacheResult(cacheName = CACHE_NAME)
    public Response findById(@PathParam("id") Long id) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("erro", "Produto não encontrado")).build();
        }
        return Response.ok(produto).build();
    }


}
