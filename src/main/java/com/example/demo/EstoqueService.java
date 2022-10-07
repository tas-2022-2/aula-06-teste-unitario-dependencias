package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    public List<Produto> reservaProdutos(List<Integer> codigos) throws ProdutoNaoEncontradoException, ProdutoSemEstoqueException {
        return List.of();
    }

    public static class ProdutoNaoEncontradoException extends Exception {
        public ProdutoNaoEncontradoException(int codigo) {
            super(String.format("Produto %d não encontrado", codigo));
        }
    }

    public static class ProdutoSemEstoqueException extends Exception {
        public ProdutoSemEstoqueException(int codigo) {
            super(String.format("Produto %d sem estoque", codigo));
        }
    }

}
