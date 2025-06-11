package br.com.agroorg.pequeno_agro.producoes.domain;


import br.com.agroorg.pequeno_agro.producoes.domain.Producao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
public class Insumos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_insumo", nullable = false, updatable = false)
    private UUID idInsumo;

    @Column(nullable = false)
    private String nomeInsumo;

    @Column(nullable = false)
    private Double quantidade;

    @Column(nullable = false)
    private String unidade; // Ex: Litros, Kg

    @Column(nullable = false)
    private LocalDate dataAplicacao;

    @Column(nullable = false)
    private String tipo; // Ex: Fertilizante, Defensivo, Semente

    @ManyToOne
    @JoinColumn(name = "id_producao")
    @JsonBackReference
    private Producao producao;

    public void setProducao(Producao producao) {
        this.producao = producao;
    }
}
