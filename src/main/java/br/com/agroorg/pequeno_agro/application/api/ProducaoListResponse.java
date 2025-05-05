package br.com.agroorg.pequeno_agro.application.api;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProducaoListResponse {

    private UUID idProducao;
    private String tipo;
    private String descricao;
}
