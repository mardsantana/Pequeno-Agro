package br.com.agroorg.pequeno_agro.producoes.application.specification;

import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoFiltro;
import br.com.agroorg.pequeno_agro.producoes.domain.Producao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ProducaoSpecs {
    public static Specification<Producao> comFiltros(ProducaoFiltro filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getTipo() != null)
                predicates.add(cb.like(cb.lower(root.get("tipo")), "%" + filtro.getTipo().toLowerCase() + "%"));

            if (filtro.getFinalizada() != null)
                predicates.add(cb.equal(root.get("finalizada"), filtro.getFinalizada()));

            if (filtro.getDataInicioDe() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataInicio"), filtro.getDataInicioDe()));

            if (filtro.getDataInicioAte() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("dataInicio"), filtro.getDataInicioAte()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}