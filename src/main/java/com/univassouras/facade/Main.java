package com.univassouras.facade;

public class Main {
    public static void main(String[] args) {
        Estoque estoque = new Estoque();
        estoque.adicionar("Notebook", 3);
        Notificacao notificacao = new Notificacao();
        LojaFacade loja = new LojaFacade(estoque, new Pagamento(), new Envio(), notificacao);

        System.out.println("1) Compra valida");
        mostrar(loja.comprar("Notebook", 1, 3000.0, "1234567890123456", "Rua das Flores, 10"));

        System.out.println();
        System.out.println("2) Quantidade maior que o estoque");
        mostrar(loja.comprar("Notebook", 5, 3000.0, "1234567890123456", "Rua das Flores, 10"));

        System.out.println();
        System.out.println("3) Cartao invalido");
        mostrar(loja.comprar("Notebook", 1, 3000.0, "123", "Rua das Flores, 10"));

        System.out.println();
        System.out.println("Estoque final de Notebook: " + estoque.consultar("Notebook"));
        System.out.println("Notificacoes enviadas:");
        for (String mensagem : notificacao.getMensagens()) {
            System.out.println("- " + mensagem);
        }
    }

    private static void mostrar(ResultadoCompra resultado) {
        System.out.println("Sucesso: " + resultado.isSucesso());
        System.out.println("Mensagem: " + resultado.getMensagem());
        if (resultado.isSucesso()) {
            System.out.println("Rastreio: " + resultado.getCodigoRastreio());
        }
    }
}
