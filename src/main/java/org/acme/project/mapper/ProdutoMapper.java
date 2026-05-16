package org.acme.project.mapper;

import org.acme.project.dto.ProdutoDTO;
import org.acme.project.model.Produto;

public class ProdutoMapper {
    public static ProdutoDTO toDTO(Produto produto) {
        if (produto == null) {
            return null;
        }
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco() != null ? produto.getPreco().toString() : null);
        dto.setEstoque(produto.getEstoque());
        return dto;
    }

    public static Produto toEntity(ProdutoDTO dto) {
        if (dto == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        try {
            produto.setPreco(dto.getPreco() != null ? new java.math.BigDecimal(dto.getPreco()) : null);
        } catch (NumberFormatException e) {
            produto.setPreco(null);
        }
        produto.setEstoque(dto.getEstoque());
        return produto;
    }
}
