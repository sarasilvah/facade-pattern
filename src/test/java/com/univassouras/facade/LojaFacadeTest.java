package com.univassouras.facade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LojaFacadeTest {

    private static final String CARTAO_VALIDO = "1234567890123456";
    private static final String ENDERECO = "Rua das Flores, 10";

    private Estoque estoque;
    private Envio envio;
    private Notificacao notificacao;
    private LojaFacade loja;

    private void montarLoja() {
        estoque = new Estoque();
        estoque.adicionar("Notebook", 3);
        envio = new Envio();
        notificacao = new Notificacao();
        loja = new LojaFacade(estoque, new Pagamento(), envio, notificacao);
    }

    @Test
    void compraValidaDeveReservarEstoqueEGerarRastreio() {
        montarLoja();

        ResultadoCompra resultado = loja.comprar("Notebook", 1, 3000.0, CARTAO_VALIDO, ENDERECO);

        assertTrue(resultado.isSucesso());
        assertEquals("BR1001", resultado.getCodigoRastreio());
        assertEquals(2, estoque.consultar("Notebook"));
    }

    @Test
    void compraValidaDeveAgendarEntregaENotificarCliente() {
        montarLoja();

        loja.comprar("Notebook", 1, 3000.0, CARTAO_VALIDO, ENDERECO);

        assertEquals(1, envio.getEntregas().size());
        assertEquals("Notebook -> Rua das Flores, 10", envio.getEntregas().get(0));
        assertEquals(1, notificacao.getMensagens().size());
        assertTrue(notificacao.getMensagens().get(0).contains("BR1001"));
    }

    @Test
    void semEstoqueNaoDeveCobrarEnviarNemNotificar() {
        montarLoja();

        ResultadoCompra resultado = loja.comprar("Notebook", 5, 3000.0, CARTAO_VALIDO, ENDERECO);

        assertFalse(resultado.isSucesso());
        assertEquals("Produto sem estoque suficiente", resultado.getMensagem());
        assertNull(resultado.getCodigoRastreio());
        assertEquals(3, estoque.consultar("Notebook"));
        assertTrue(envio.getEntregas().isEmpty());
        assertTrue(notificacao.getMensagens().isEmpty());
    }

    @Test
    void produtoInexistenteDeveSerTratadoComoSemEstoque() {
        montarLoja();

        ResultadoCompra resultado = loja.comprar("Tablet", 1, 1000.0, CARTAO_VALIDO, ENDERECO);

        assertFalse(resultado.isSucesso());
        assertEquals("Produto sem estoque suficiente", resultado.getMensagem());
    }

    @Test
    void cartaoInvalidoDeveDevolverOEstoqueENotificarARecusa() {
        montarLoja();

        ResultadoCompra resultado = loja.comprar("Notebook", 1, 3000.0, "123", ENDERECO);

        assertFalse(resultado.isSucesso());
        assertEquals("Pagamento recusado", resultado.getMensagem());
        assertEquals(3, estoque.consultar("Notebook"));
        assertTrue(envio.getEntregas().isEmpty());
        assertEquals(1, notificacao.getMensagens().size());
        assertTrue(notificacao.getMensagens().get(0).contains("recusado"));
    }

    @Test
    void valorAcimaDoLimiteDeveSerRecusadoEManterOEstoque() {
        montarLoja();

        ResultadoCompra resultado = loja.comprar("Notebook", 1, 6000.0, CARTAO_VALIDO, ENDERECO);

        assertFalse(resultado.isSucesso());
        assertEquals(3, estoque.consultar("Notebook"));
    }

    @Test
    void codigosDeRastreioDevemSerSequenciais() {
        montarLoja();

        ResultadoCompra primeira = loja.comprar("Notebook", 1, 3000.0, CARTAO_VALIDO, ENDERECO);
        ResultadoCompra segunda = loja.comprar("Notebook", 1, 3000.0, CARTAO_VALIDO, ENDERECO);

        assertEquals("BR1001", primeira.getCodigoRastreio());
        assertEquals("BR1002", segunda.getCodigoRastreio());
        assertEquals(1, estoque.consultar("Notebook"));
    }
}
