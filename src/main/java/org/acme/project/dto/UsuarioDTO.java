package org.acme.project.dto;
import org.acme.project.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.project.validation.ValidateEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
    @NotNull(message = "Role é obrigatória")
    @ValidateEnum(enumClass = RoleEnum.class, message = "Role inválida")
    private RoleEnum role;
}
