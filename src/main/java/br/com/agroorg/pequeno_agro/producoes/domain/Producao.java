package br.com.agroorg.pequeno_agro.producoes.domain;


import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoRequest;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Insumos> insumos;
    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Equipamento> equipamentos;
    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Funcionario> funcionarios;



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

    public void setInsumos(List<Insumos> insumos) {
        this.insumos = insumos;
        if (insumos != null) {
            for (Insumos insumo : insumos) {
                insumo.setProducao(this); // mantém vínculo bidirecional
            }
        }
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
        if (equipamentos != null){
            for (Equipamento equipamento : equipamentos){
                equipamento.setProducao(this);
            }
        }
    }

    public Double getTotalHorasEquipamentos() {
        if (equipamentos == null) return 0.0;
        return equipamentos.stream()
                .mapToDouble(e -> e.getTempoUtilizacao() != null ? e.getTempoUtilizacao() : 0.0)
                .sum();
    }

    public String getClassificacaoEquipamentos() {
        double horas = getTotalHorasEquipamentos();
        if (horas == 0.0) return "Sem uso";
        if (horas < 10) return "Pouco uso";
        if (horas < 50) return "Uso moderado";
        return "Uso intenso";
    }

    public Double getCustoTotalMaoDeObra() {
        return funcionarios.stream()
                .mapToDouble(Funcionario::getCustoTotal)
                .sum();
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        if (funcionarios != null){
            for (Funcionario funcionario : funcionarios){
                funcionario.setProducao(this);
            }
        }
    }
}
