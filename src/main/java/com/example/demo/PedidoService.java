package com.example.demo;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.CupomService.CupomIndisponivelException;
import com.example.demo.EstoqueService.ProdutoNaoEncontradoException;
import com.example.demo.EstoqueService.ProdutoSemEstoqueException;

// Teste Unitário: PedidoService

@Service
public class PedidoService {
    // stubs,...,mocks
    private final EstoqueService estoqueService;
    private final CupomService cupomService;
    private final int maxTentativasCupom;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(
        EstoqueService estoqueService, 
        CupomService cupomService,
        PedidoRepository pedidoRepository,
        @Value("${cupom.tentativas:3}") int maxTentativasCupom) {
            
        this.estoqueService = estoqueService;
        this.cupomService = cupomService;
        this.maxTentativasCupom = maxTentativasCupom;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido submeter(String usuario, List<Integer> codigos) throws ProdutoNaoEncontradoException, ProdutoSemEstoqueException, CupomIndisponivelException {
        // 400
        List<Produto> produtos = estoqueService.reservaProdutos(codigos); // reservar por 5min

        int tentativa = 1;

        List<Cupom> cupons = List.of();

        List<UUID> cuponsSelecionados = List.of();

        while(true) {
            cupons = cupomService.buscaCupons(usuario);
            
            // TODO: priorizar cupons de maior valor desde que não pague a totalidade, se pagar 399
            // 500, 400, [300, 80], 45, 45
            
            cuponsSelecionados = cupons.stream().map(Cupom::getId).toList();

            try {
                cupomService.usaCupons(cuponsSelecionados);
                break;

            } catch (CupomIndisponivelException e) {
                if (tentativa++ > maxTentativasCupom) {
                    estoqueService.liberarProdutos(codigos);
                    throw e;
                }
            } catch (Exception e) {
                System.err.println(e);
                estoqueService.liberarProdutos(codigos);
                throw new RuntimeException(e);
            }
        }

        estoqueService.confirmaProdutos(codigos);

        // TODO: hack
        final List<UUID> selecionados = cuponsSelecionados;

        int total = produtos.stream().map(Produto::getValor).reduce(0, Integer::sum);
        int totalCupons = cupons.stream().filter(c -> selecionados.contains(c.getId())).map(Cupom::getValor).reduce(0, Integer::sum);

        Pedido pedido = new Pedido(total, totalCupons);

        pedidoRepository.save(pedido);

        return pedido;
    }
}
