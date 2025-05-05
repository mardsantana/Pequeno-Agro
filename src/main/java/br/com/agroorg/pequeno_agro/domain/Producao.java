package br.com.agroorg.pequeno_agro.domain;


import br.com.agroorg.pequeno_agro.application.api.ProducaoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producao {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProducao;
    @Column(nullable = false)
    private String tipo;
    private String descricao;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "data_fim")
    private LocalDate dataFim;
    @Column(nullable = false)
    private Double area;



    @Column(name = "duracao_dias")
    private Long duracaoDias;
    @Column(name = "classificacao_duracao", length = 20)
    private String classificacaoDuracao;
    @Column(name = "finalizada")
    private Boolean finalizada;

    @PrePersist
    @PreUpdate
    private void preSalvarOuAtualizar() {
        calcularAtributosDerivados();
    }

    public void calcularAtributosDerivados() {
        this.duracaoDias = calcularDuracao();
        this.classificacaoDuracao = classificarDuracao();
        this.finalizada = verificarFinalizacao();
    }

    private long calcularDuracao() {
        LocalDate fim = (dataFim != null) ? dataFim : LocalDate.now();
        return ChronoUnit.DAYS.between(dataInicio, fim);
    }

    private String classificarDuracao() {
        long dias = calcularDuracao();
        if (dias <= 60) return "Curta";
        if (dias <= 180) return "Média";
        return "Longa";
    }

    private boolean verificarFinalizacao() {
        return dataFim != null && dataFim.isBefore(LocalDate.now());
    }

    public void atualiza(ProducaoRequest request) {
        this.tipo = request.getTipo();
        this.descricao = request.getDescricao();
        this.dataInicio = request.getDataInicio();
        this.dataFim = request.getDataFim();
        this.area = request.getArea();
    }
}
