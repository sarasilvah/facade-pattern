package com.univassouras.facade;

public class ResultadoCompra {
    private final boolean sucesso;
    private final String mensagem;
    private final String codigoRastreio;

    private ResultadoCompra(boolean sucesso, String mensagem, String codigoRastreio) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.codigoRastreio = codigoRastreio;
    }

    public static ResultadoCompra aprovada(String codigoRastreio) {
        return new ResultadoCompra(true, "Compra realizada com sucesso", codigoRastreio);
    }

    public static ResultadoCompra recusada(String motivo) {
        return new ResultadoCompra(false, motivo, null);
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }
}
