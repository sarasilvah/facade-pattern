package com.univassouras.facade;

import java.util.ArrayList;
import java.util.List;

public class Notificacao {
    private final List<String> mensagens = new ArrayList<>();

    public void enviar(String mensagem) {
        mensagens.add(mensagem);
    }

    public List<String> getMensagens() {
        return new ArrayList<>(mensagens);
    }
}
