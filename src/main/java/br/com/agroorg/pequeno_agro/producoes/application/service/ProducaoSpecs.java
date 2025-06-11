package br.com.agroorg.pequeno_agro.producoes.application.service;

import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoFiltro;
import br.com.agroorg.pequeno_agro.producoes.domain.Producao;
import org.springframework.data.jpa.domain.Specification;

public class ProducaoSpecs {

    public static Specification<Producao> comFiltros(ProducaoFiltro filtro) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (filtro.getTipo() != null && !filtro.getTipo().isBlank()) {
                predicates.getExpressions().add(cb.like(cb.lower(root.get("tipo")), "%" + filtro.getTipo().toLowerCase() + "%"));
            }
            if (filtro.getFinalizada() != null) {
                predicates.getExpressions().add(cb.equal(root.get("finalizada"), filtro.getFinalizada()));
            }
            if (filtro.getDataInicioDe() != null) {
                predicates.getExpressions().add(cb.greaterThanOrEqualTo(root.get("dataInicio"), filtro.getDataInicioDe()));
            }
            if (filtro.getDataInicioAte() != null) {
                predicates.getExpressions().add(cb.lessThanOrEqualTo(root.get("dataInicio"), filtro.getDataInicioAte()));
            }

            return predicates;
        };
    }
}
