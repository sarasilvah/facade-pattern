package com.univassouras.facade;

import java.util.ArrayList;
import java.util.List;

public class Envio {
    private final List<String> entregas = new ArrayList<>();
    private int contador;

    public String agendar(String produto, String endereco) {
        contador++;
        entregas.add(produto + " -> " + endereco);
        return "BR" + (1000 + contador);
    }

    public List<String> getEntregas() {
        return new ArrayList<>(entregas);
    }
}
