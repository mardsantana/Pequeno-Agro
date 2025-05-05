package br.com.agroorg.pequeno_agro.application.service;


import br.com.agroorg.pequeno_agro.application.api.ProducaoFiltro;
import br.com.agroorg.pequeno_agro.application.api.ProducaoListResponse;
import br.com.agroorg.pequeno_agro.application.api.ProducaoRequest;
import br.com.agroorg.pequeno_agro.application.api.ProducaoResponse;
import br.com.agroorg.pequeno_agro.application.mapper.ProducaoMapper;
import br.com.agroorg.pequeno_agro.domain.Producao;
import br.com.agroorg.pequeno_agro.handler.APIException;
import br.com.agroorg.pequeno_agro.application.repository.ProducaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducaoServiceImpl implements IProducaoService {
    private final ProducaoRepository repository;

    @Override
    public ProducaoResponse criarProducao(ProducaoRequest request) {
        log.info("[start] ProducaoServiceImpl - criarProducao");

        Producao producao = ProducaoMapper.toEntity(request);
        Producao salva = repository.save(producao);

        log.info("Produção criada com ID: {}", salva.getIdProducao());
        log.info("[finish] ProducaoServiceImpl - criarProducao");
        return ProducaoMapper.toResponse(salva);
    }

    @Override
    public List<ProducaoListResponse> listarProducoes() {
        log.info("[start] ProducaoServiceImpl - listarProducoes");
        List<ProducaoListResponse> lista = repository.findAll().stream()
                .map(ProducaoMapper::toListResponse)
                .collect(Collectors.toList());
        log.info("Listagem finalizada com {} itens", lista.size());
        log.info("[finish] ProducaoServiceImpl - listarProducoes");
        return lista;
    }

    @Override
    public ProducaoResponse buscarPorId(UUID idProducao) {
        log.info("[start] ProducaoServiceImpl - buscarPorId: {}", idProducao);
        Producao producao = repository.findById(idProducao)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND,"Produção não encontrada"));
        log.info("Produção encontrada - ID: {}", idProducao);
        log.info("[finish] ProducaoServiceImpl - buscarPorId");
        return ProducaoMapper.toResponse(producao);
    }

    @Override
    public Page<ProducaoResponse> buscarComFiltros(ProducaoFiltro filtro, Pageable pageable) {
        log.info("[start] ProducaoServiceImpl - buscarComFiltros");
        log.debug("Filtros recebidos - tipo: {}, finalizada: {}, dataInicioDe: {}, dataInicioAte: {}, page: {}, size: {}, sort: {}",
                filtro.getTipo(), filtro.getFinalizada(), filtro.getDataInicioDe(), filtro.getDataInicioAte(),
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Producao> resultados = repository.findAll(ProducaoSpecs.comFiltros(filtro), pageable);
        log.info("Busca finalizada. Total encontrados: {}", resultados.getTotalElements());
        log.info("[finish] ProducaoServiceImpl - buscarComFiltros");
        return resultados.map(ProducaoMapper::toResponse);
    }

    @Override
    public void atualizaProducao(UUID idProducao, ProducaoRequest request) {
        log.info("[start] ProducaoServiceImpl - atualizaProducao: {}", idProducao);
        repository.findById(idProducao).ifPresent(producao -> {
            producao.atualiza(request);
            repository.save(producao);
        });
        log.info("[finish] ProducaoServiceImpl - atualizaProducao: {}", idProducao);
    }

    @Override
    public void deletaProducao(UUID idProducao) {
        log.info("[start] ProducaoServiceImpl - deletaProducao: {}", idProducao);
        repository.deleteById(idProducao);
        log.info("[finish] ProducaoServiceImpl - deletaProducao: {}", idProducao);
    }

}
