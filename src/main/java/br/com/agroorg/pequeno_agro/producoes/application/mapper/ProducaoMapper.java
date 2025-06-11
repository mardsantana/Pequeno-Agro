package br.com.agroorg.pequeno_agro.producoes.application.mapper;

import br.com.agroorg.pequeno_agro.producoes.domain.Equipamento;
import br.com.agroorg.pequeno_agro.producoes.domain.Funcionario;
import br.com.agroorg.pequeno_agro.producoes.domain.Insumos;
import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoListResponse;
import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoRequest;
import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoResponse;
import br.com.agroorg.pequeno_agro.producoes.domain.Producao;

import java.util.List;
import java.util.UUID;

public class ProducaoMapper {

    private ProducaoMapper() {
        // Utilitário estático
    }

    public static ProducaoResponse toResponse(Producao p) {
        return ProducaoResponse.builder()
                .idProducao(p.getIdProducao())
                .tipo(p.getTipo())
                .descricao(p.getDescricao())
                .dataInicio(p.getDataInicio())
                .dataFim(p.getDataFim())
                .area(p.getArea())
                .duracaoDias(p.getDuracaoDias())
                .classificacaoDuracao(p.getClassificacaoDuracao())
                .finalizada(p.getFinalizada())
                .insumos(p.getInsumos())
                .equipamentos(p.getEquipamentos())
                .totalHorasEquipamentos(p.getTotalHorasEquipamentos())
                .classificacaoEquipamentos(p.getClassificacaoEquipamentos())
                .funcionarios(p.getFuncionarios()) // <--- diretamente os funcionários da entidade
                .custoTotalMaoDeObra(p.getCustoTotalMaoDeObra())
                .build();
    }

    public static Producao toEntity(ProducaoRequest request) {
        Producao producao = Producao.builder()
                .tipo(request.getTipo())
                .descricao(request.getDescricao())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .area(request.getArea())
                .build();

        if (request.getInsumos() != null) {
            List<Insumos> insumos = request.getInsumos().stream()
                    .map(insumoReq -> Insumos.builder()
                            .nomeInsumo(insumoReq.getNomeInsumo())
                            .quantidade(insumoReq.getQuantidade())
                            .unidade(insumoReq.getUnidade())
                            .dataAplicacao(insumoReq.getDataAplicacao())
                            .tipo(insumoReq.getTipo())
                            .producao(producao) // importante para manter o vínculo bidirecional
                            .build())
                    .toList();

            producao.setInsumos(insumos);
        }

        if (request.getEquipamentos() != null) {
            List<Equipamento> equipamentos = request.getEquipamentos().stream()
                    .map(equipReq -> Equipamento.builder()
                            .nomeEquipamento(equipReq.getNomeEquipamento())
                            .tipo(equipReq.getTipo())
                            .tempoUtilizacao(equipReq.getTempoUtilizacao())
                            .dataUtilizacao(equipReq.getDataUtilizacao())
                            .producao(producao)
                            .build())
                    .toList();

            producao.setEquipamentos(equipamentos);
        }

        if (request.getFuncionarios() != null) {
            List<Funcionario> funcionarios = request.getFuncionarios().stream()
                    .map(f -> Funcionario.builder()
                            .nome(f.getNome())
                            .numeroMatricula("MAT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                            .funcao(f.getFuncao())
                            .diaria(f.getDiaria())
                            .horasTrabalhadas(f.getHorasTrabalhadas())
                            .custoHora(f.getCustoHora())
                            .dataAtuacao(f.getDataAtuacao())
                            .producao(producao)
                            .build())
                    .toList();
            producao.setFuncionarios(funcionarios);
        }

        producao.calcularAtributosDerivados();
        return producao;
    }

    public static ProducaoListResponse toListResponse(Producao p) {
        return ProducaoListResponse.builder()
                .idProducao(p.getIdProducao())
                .tipo(p.getTipo())
                .descricao(p.getDescricao())
                .build();
    }
}
