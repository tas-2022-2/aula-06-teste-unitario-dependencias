package com.example.demo;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CupomService {

    public List<Cupom> buscaCupons(String usuario) {
        return List.of();
    }

    public void usaCupons(List<UUID> cuponsSelecionados) throws CupomIndisponivelException {
    }


    public class CupomIndisponivelException extends Exception {
        private final UUID cupom;

        public CupomIndisponivelException(UUID cupom) {
            super(cupom + " indisponível");
            this.cupom = cupom;
        }

        public UUID getCupom() {
            return cupom;
        }
    }
}
