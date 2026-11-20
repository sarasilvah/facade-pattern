package com.univassouras.facade;

public class LojaFacade {
    private final Estoque estoque;
    private final Pagamento pagamento;
    private final Envio envio;
    private final Notificacao notificacao;

    public LojaFacade(Estoque estoque, Pagamento pagamento, Envio envio, Notificacao notificacao) {
        this.estoque = estoque;
        this.pagamento = pagamento;
        this.envio = envio;
        this.notificacao = notificacao;
    }

    public ResultadoCompra comprar(String produto, int quantidade, double precoUnitario, String cartao, String endereco) {
        if (!estoque.disponivel(produto, quantidade)) {
            return ResultadoCompra.recusada("Produto sem estoque suficiente");
        }

        estoque.reservar(produto, quantidade);

        double total = precoUnitario * quantidade;
        if (!pagamento.processar(cartao, total)) {
            estoque.devolver(produto, quantidade);
            notificacao.enviar("Pagamento recusado para " + produto);
            return ResultadoCompra.recusada("Pagamento recusado");
        }

        String codigoRastreio = envio.agendar(produto, endereco);
        notificacao.enviar("Compra confirmada: " + produto + " (rastreio " + codigoRastreio + ")");
        return ResultadoCompra.aprovada(codigoRastreio);
    }
}
