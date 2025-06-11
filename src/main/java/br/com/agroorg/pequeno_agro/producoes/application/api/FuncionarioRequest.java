package br.com.agroorg.pequeno_agro.producoes.application.api;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class FuncionarioRequest {
    private String nome;
    private String funcao;
    private Double diaria;
    private Double horasTrabalhadas;
    private Double custoHora;
    private LocalDate dataAtuacao;
}
