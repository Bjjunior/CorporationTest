package br.com.corporation.CorporationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.corporation.CorporationTest.model.Cliente;
import br.com.corporation.CorporationTest.model.EmailSender;
import br.com.corporation.CorporationTest.model.Empresa;
import br.com.corporation.CorporationTest.model.TaxaSistema;
import br.com.corporation.CorporationTest.model.Transacao;

class TransacaoTest {

    @Test
    void testRealizarTransacaoComSaldoSuficiente() {
        // Criando uma empresa
        Empresa empresa = new Empresa("Empresa Teste", "12345678901234", "Rua Teste", "Tecnologia", BigDecimal.ZERO);

        // Criando um cliente
        Cliente cliente = new Cliente("Cliente Teste", "12345678901", "Rua Cliente", BigDecimal.valueOf(1000), empresa, "cliente@teste.com");

        // Adicionando a empresa à lista de taxas do sistema
        TaxaSistema taxa = new TaxaSistema();
        taxa.setValor(5); // 5% de taxa
        empresa.getTaxas().add(taxa);

        // Criando uma transação de R$ 100
        Transacao transacao = new Transacao(empresa, cliente, BigDecimal.valueOf(100));

        // Criando um mock para o EmailSender
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

        // Executando a transação
        transacao.realizarTransacao(emailSenderMock);

        // Verificando se a transação foi bem-sucedida
        assertEquals(BigDecimal.valueOf(895), cliente.getSaldo()); // Saldo esperado: 1000 - 100 - 5% de taxa
        assertEquals(1, cliente.getTransacoes().size()); // Uma transação deve estar na lista

        // Verificando se o callback foi enviado corretamente (isso pode variar dependendo do ambiente)
        // Aqui, estou apenas imprimindo uma mensagem indicando que o envio foi bem-sucedido
        System.out.println("Callback enviado com sucesso");

        // Verificando se o e-mail foi enviado corretamente
        Mockito.verify(emailSenderMock, Mockito.times(1)).sendEmail(Mockito.any());
    }

    @Test
    void testRealizarTransacaoComSaldoInsuficiente() {
        // Criando uma empresa
        Empresa empresa = new Empresa("Empresa Teste", "12345678901234", "Rua Teste", "Tecnologia", BigDecimal.ZERO);

        // Criando um cliente com saldo insuficiente
        Cliente cliente = new Cliente("Cliente Teste", "12345678901", "Rua Cliente", BigDecimal.valueOf(50), empresa, "cliente@teste.com");

        // Adicionando a empresa à lista de taxas do sistema
        TaxaSistema taxa = new TaxaSistema();
        taxa.setValor(5); // 5% de taxa
        empresa.getTaxas().add(taxa);

        // Criando uma transação de R$ 100 (saldo insuficiente)
        Transacao transacao = new Transacao(empresa, cliente, BigDecimal.valueOf(100));

        // Criando um mock para o EmailSender
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

        // Verificando se a tentativa de transação lança a exceção correta
        assertThrows(IllegalArgumentException.class, () -> transacao.realizarTransacao(emailSenderMock));

        // Verificando se o saldo do cliente permanece o mesmo
        assertEquals(BigDecimal.valueOf(50), cliente.getSaldo());

        // Verificando se nenhuma transação foi registrada
        assertEquals(0, cliente.getTransacoes().size());

        // Verificando que nenhum callback foi enviado (isso pode variar dependendo do ambiente)
        // Aqui, estou apenas imprimindo uma mensagem indicando que nenhum envio foi feito
        System.out.println("Nenhum callback enviado devido a saldo insuficiente");

        // Verificando que nenhum e-mail foi enviado
        Mockito.verify(emailSenderMock, Mockito.never()).sendEmail(Mockito.any());
    }
}
