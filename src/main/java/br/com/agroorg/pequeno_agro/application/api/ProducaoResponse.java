package br.com.agroorg.pequeno_agro.application.api;

import br.com.agroorg.pequeno_agro.domain.Producao;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
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


    public static ProducaoResponse from(Producao producao) {
        Long duracao = calculaDuracaoDias(producao);
        return ProducaoResponse.builder()
                .idProducao(producao.getIdProducao())
                .tipo(producao.getTipo())
                .descricao(producao.getDescricao())
                .dataInicio(producao.getDataInicio())
                .dataFim(producao.getDataFim())
                .area(producao.getArea())
                .duracaoDias(duracao)
                .classificacaoDuracao(classificaDuracao(duracao))
                .finalizada(isFinalizada(producao))
                .build();
    }

    private static boolean isFinalizada(Producao producao) {
        return producao.getDataFim() != null && !producao.getDataFim().isAfter(LocalDate.now());
    }

    private static Long calculaDuracaoDias(Producao producao) {
        if (producao.getDataInicio() != null && producao.getDataFim() != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(
                    producao.getDataInicio(), producao.getDataFim()
            );
        }
        return null;
    }

    private static String classificaDuracao(Long dias) {
        if (dias == null) return "Desconhecida";
        if (dias < 30) return "Curta";
        if (dias < 180) return "Média";
        return "Longa";
    }
}
