package br.com.agroorg.pequeno_agro.producoes.domain;

import br.com.agroorg.pequeno_agro.producoes.domain.Producao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_equipamento", nullable = false, updatable = false)
    private UUID idEquipamento;

    @Column(nullable = false)
    private String nomeEquipamento;

    @Column(nullable = false)
    private String tipo; // Ex: Trator, Drone, Colheitadeira

    @Column(nullable = false)
    private Double tempoUtilizacao; // em horas

    @Column(nullable = false)
    private LocalDate dataUtilizacao;

    @ManyToOne
    @JoinColumn(name = "id_producao")
    @JsonBackReference
    private Producao producao;

    public void setProducao(Producao producao) {
        this.producao = producao;
    }
}
