package br.com.agroorg.pequeno_agro.repository;

import br.com.agroorg.pequeno_agro.domain.Producao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProducaoRepository extends JpaRepository<Producao, UUID>, JpaSpecificationExecutor<Producao> {
}
