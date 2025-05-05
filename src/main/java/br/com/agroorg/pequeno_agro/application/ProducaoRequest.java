package br.com.agroorg.pequeno_agro.application;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@ToString
public class ProducaoRequest {

    @NotBlank(message = "O tipo da produção é obrigatório.")
    @Size(max = 100, message = "O tipo da produção deve ter no máximo 100 caracteres.")
    private String tipo;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    @NotNull(message = "A data de início é obrigatória.")
    @PastOrPresent(message = "A data de início não pode ser no futuro.")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @NotNull(message = "A área é obrigatória.")
    @Positive(message = "A área deve ser um valor positivo.")
    @DecimalMax(value = "1000.0", message = "A área não pode ser maior que 1000 hectares.")
    private Double area;

    @AssertTrue(message = "A data de fim não pode ser anterior à data de início.")
    public boolean isDataFimValida() {
        return dataFim == null || !dataFim.isBefore(dataInicio);
    }
}
