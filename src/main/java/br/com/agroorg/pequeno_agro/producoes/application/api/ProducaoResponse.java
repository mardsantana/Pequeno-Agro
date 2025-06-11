package br.com.agroorg.pequeno_agro.producoes.application.api;

import br.com.agroorg.pequeno_agro.producoes.domain.Equipamento;
import br.com.agroorg.pequeno_agro.producoes.domain.Funcionario;
import br.com.agroorg.pequeno_agro.producoes.domain.Insumos;
import br.com.agroorg.pequeno_agro.producoes.domain.Producao;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Builder
public class ProducaoResponse {

    private UUID idProducao;
    private String tipo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Double area;
    private Long duracaoDias;
    private String classificacaoDuracao;
    private Boolean finalizada;

    private List<Insumos> insumos;
    private List<Equipamento> equipamentos;
    private List<Funcionario> funcionarios;


    private Double custoTotalMaoDeObra;
    private Double totalHorasEquipamentos;
    private String classificacaoEquipamentos;

    public static ProducaoResponse from(Producao producao) {
        return ProducaoResponse.builder()
                .idProducao(producao.getIdProducao())
                .tipo(producao.getTipo())
                .descricao(producao.getDescricao())
                .dataInicio(producao.getDataInicio())
                .dataFim(producao.getDataFim())
                .area(producao.getArea())
                .duracaoDias(producao.getDuracaoDias())
                .classificacaoDuracao(producao.getClassificacaoDuracao())
                .finalizada(producao.getFinalizada())
                .insumos(producao.getInsumos())
                .equipamentos(producao.getEquipamentos())
                .totalHorasEquipamentos(producao.getTotalHorasEquipamentos())
                .classificacaoEquipamentos(producao.getClassificacaoEquipamentos())
                .build();
    }
}
