package org.acme.project.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.acme.project.enums.RoleEnum;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Usuario extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150, nullable = false)
    private String nome;
    @Column(length = 150, nullable = false, unique = true)
    private String email;
    @Column(length = 150, nullable = false)
    private String senha;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
