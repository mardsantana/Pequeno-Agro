package br.com.agroorg.pequeno_agro.producoes.application.service;

import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoFiltro;
import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoListResponse;
import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoRequest;
import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoResponse;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface IProducaoService {
    ProducaoResponse criarProducao(ProducaoRequest request);
    List<ProducaoListResponse> listarProducoes();
    ProducaoResponse buscarPorId(UUID idProducao);
    Page buscarComFiltros(ProducaoFiltro filtro, Pageable pageable);
    void atualizaProducao(UUID idProducao, ProducaoRequest request);
    void deletaProducao(UUID idProducao);
    String gerarCsvComFiltros(ProducaoFiltro filtro);
    byte[] gerarPdf(ProducaoFiltro filtro) throws Exception;
}
