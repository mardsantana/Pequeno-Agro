package br.com.agroorg.pequeno_agro.producoes.application.api;

import br.com.agroorg.pequeno_agro.producoes.domain.Equipamento;
import br.com.agroorg.pequeno_agro.producoes.domain.Funcionario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


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

    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InsumosRequest> insumos;
    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipamentoRequest> equipamentos;
    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionarioRequest> funcionarios;

    @AssertTrue(message = "A data de fim não pode ser anterior à data de início.")
    public boolean isDataFimValida() {
        return dataFim == null || !dataFim.isBefore(dataInicio);
    }
}
