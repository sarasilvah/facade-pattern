package com.univassouras.facade;

public class Pagamento {
    private static final double LIMITE = 5000.0;

    public boolean processar(String cartao, double valor) {
        boolean cartaoValido = cartao != null && cartao.matches("\\d{16}");
        return cartaoValido && valor > 0 && valor <= LIMITE;
    }
}
