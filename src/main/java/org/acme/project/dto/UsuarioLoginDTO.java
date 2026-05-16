package org.acme.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.project.enums.RoleEnum;
import org.acme.project.validation.ValidateEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioLoginDTO {
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
}
