package br.com.agroorg.pequeno_agro.producoes.application.api;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class InsumosRequest {
    private String nomeInsumo;
    private Double quantidade;
    private String unidade;
    private LocalDate dataAplicacao;
    private String tipo;
}
