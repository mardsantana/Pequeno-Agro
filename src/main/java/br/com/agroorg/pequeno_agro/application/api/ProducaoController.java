package br.com.agroorg.pequeno_agro.application.api;


import br.com.agroorg.pequeno_agro.application.service.IProducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/producao")
@RequiredArgsConstructor
@Slf4j
public class ProducaoController {

    private final IProducaoService producaoService;

    @PostMapping
    public ResponseEntity<ProducaoResponse> criar(@RequestBody @Valid ProducaoRequest request) {
        log.info("[start] ProducaoController - criar");
        ProducaoResponse response = producaoService.criarProducao(request);
        log.info("Produção criada: ID={}", response.getIdProducao());
        log.info("[finish] ProducaoController - criar");
        return ResponseEntity.created(URI.create("/agro-peq/producao/" + response.getIdProducao())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProducaoListResponse>> listarTodas() {
        log.info("[start] ProducaoController - listarTodas");
        List<ProducaoListResponse> lista = producaoService.listarProducoes();
        log.info("Total produções encontradas: {}", lista.size());
        log.info("[finish] ProducaoController - listarTodas");
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idProducao}")
    public ResponseEntity<ProducaoResponse> buscarPorId(@PathVariable UUID idProducao) {
        log.info("[start] ProducaoController - buscarPorId: {}", idProducao);
        ProducaoResponse response = producaoService.buscarPorId(idProducao);
        log.info("Produção encontrada: descrição={}", response.getDescricao());
        log.info("[finish] ProducaoController - buscarPorId");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<ProducaoResponse>> filtrar(@ModelAttribute ProducaoFiltro filtro, Pageable pageable) {
        log.info("[start] ProducaoController - filtrar");
        log.debug("Filtro aplicado: tipo={}, finalizada={}, dataInicioDe={}, dataInicioAte={}, page={}, size={}, sort={}",
                filtro.getTipo(), filtro.getFinalizada(), filtro.getDataInicioDe(), filtro.getDataInicioAte(),
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<ProducaoResponse> pagina = producaoService.buscarComFiltros(filtro, pageable);
        log.info("Página retornada: número={}, elementos={}", pagina.getNumber() + 1, pagina.getNumberOfElements());
        log.info("[finish] ProducaoController - filtrar");
        return ResponseEntity.ok(pagina);
    }

    @PutMapping("upDate/{idProducao}")
    void atualiza(@PathVariable UUID idProducao, @RequestBody @Valid ProducaoRequest request) {
        log.info("[start] ProducaoController - atualizar: {}", idProducao);
        producaoService.atualizaProducao(idProducao, request);
        log.info("[finish] ProducaoController - atualizar");
    }

    @DeleteMapping("delete/{idProducao}")
    void deleta(@PathVariable UUID idProducao) {
        log.info("[start] ProducaoController - deleta: {}", idProducao);
        producaoService.deletaProducao(idProducao);
        log.info("[finish] ProducaoController - deleta");
    }

}
