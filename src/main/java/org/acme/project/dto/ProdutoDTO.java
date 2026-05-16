package org.acme.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDTO {
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private String descricao;
    private String preco;
    private int estoque;
}
