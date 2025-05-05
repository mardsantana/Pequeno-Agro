package br.com.agroorg.pequeno_agro.application.mapper;

import br.com.agroorg.pequeno_agro.application.ProducaoListResponse;
import br.com.agroorg.pequeno_agro.application.ProducaoRequest;
import br.com.agroorg.pequeno_agro.application.ProducaoResponse;
import br.com.agroorg.pequeno_agro.domain.Producao;

public class ProducaoMapper {

    private ProducaoMapper() {
        // Utilitário estático
    }

    public static Producao toEntity(ProducaoRequest request) {
        Producao producao = Producao.builder()
                .tipo(request.getTipo())
                .descricao(request.getDescricao())
                .dataInicio(request.getDataInicio())
                .dataFim(request.getDataFim())
                .area(request.getArea())
                .build();

        producao.calcularAtributosDerivados(); // aplica lógica de domínio

        return producao;
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
                .build();
    }

    public static ProducaoListResponse toListResponse(Producao p) {
        return ProducaoListResponse.builder()
                .idProducao(p.getIdProducao())
                .tipo(p.getTipo())
                .descricao(p.getDescricao())
                .build();
    }
}
