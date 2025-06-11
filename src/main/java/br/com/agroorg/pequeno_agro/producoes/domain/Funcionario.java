package br.com.agroorg.pequeno_agro.producoes.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFuncionario;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String numeroMatricula;

    @NotBlank
    @Column(nullable = false)
    private String funcao;

    @PositiveOrZero
    private Double diaria;

    @PositiveOrZero
    private Double horasTrabalhadas;

    @PositiveOrZero
    private Double custoHora;

    @PastOrPresent
    private LocalDate dataAtuacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producao_id", nullable = false)
    @JsonBackReference
    private Producao producao;

    public Double getCustoTotal() {
        if (diaria != null) {
            return diaria;
        }
        if (horasTrabalhadas != null && custoHora != null) {
            return horasTrabalhadas * custoHora;
        }
        return 0.0;
    }

    public void setProducao(Producao producao) {
        this.producao = producao;
    }
}