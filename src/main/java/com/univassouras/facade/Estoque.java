package com.univassouras.facade;

import java.util.HashMap;
import java.util.Map;

public class Estoque {
    private final Map<String, Integer> quantidades = new HashMap<>();

    public void adicionar(String produto, int quantidade) {
        quantidades.merge(produto, quantidade, Integer::sum);
    }

    public boolean disponivel(String produto, int quantidade) {
        return consultar(produto) >= quantidade;
    }

    public void reservar(String produto, int quantidade) {
        quantidades.put(produto, consultar(produto) - quantidade);
    }

    public void devolver(String produto, int quantidade) {
        quantidades.put(produto, consultar(produto) + quantidade);
    }

    public int consultar(String produto) {
        return quantidades.getOrDefault(produto, 0);
    }
}
