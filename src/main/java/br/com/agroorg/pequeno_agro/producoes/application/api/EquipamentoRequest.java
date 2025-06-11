package br.com.agroorg.pequeno_agro.producoes.application.api;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class EquipamentoRequest {

    private String nomeEquipamento;
    private String tipo;               // Ex: Trator, Drone, Colheitadeira
    private Double tempoUtilizacao;    // em horas
    private LocalDate dataUtilizacao;
}
