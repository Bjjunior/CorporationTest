package br.com.corporation.CorporationTest.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Classe que representa uma transação financeira entre uma empresa e um cliente.
 *
 * Esta classe registra as informações da transação, como a empresa envolvida,
 * o cliente envolvido, o valor da transação e métodos para realizar a transação
 * considerando as taxas de sistema, enviar callback para a empresa e notificar o cliente por e-mail.
 *
 * @version 1.0
 */
@Entity
@SequenceGenerator(name = "transacao", sequenceName = "SQ_T_TRANSACAO", allocationSize = 1)
public class Transacao {

    /**
     * Identificador único da transação.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "transacao")
    private Long id;

    /**
     * Empresa envolvida na transação.
     */
    @ManyToOne
    @NotNull
    private Empresa empresa;

    /**
     * Cliente envolvido na transação.
     */
    @ManyToOne
    @NotNull
    private Cliente cliente;

    /**
     * Valor da transação.
     */
    @NotNull
    @Column(columnDefinition = "DECIMAL(19,2)")
    private BigDecimal valor;

    /**
     * Construtor padrão da classe.
     */
    public Transacao() {
    }

    /**
     * Construtor com parâmetros para inicializar uma transação.
     *
     * @param empresa A empresa envolvida na transação.
     * @param cliente O cliente envolvido na transação.
     * @param valor   O valor da transação.
     */
    public Transacao(Empresa empresa, Cliente cliente, BigDecimal valor) {
        this.empresa = empresa;
        this.cliente = cliente;
        this.valor = valor;
    }

    /**
     * Obtém o identificador único da transação.
     *
     * @return O identificador único da transação.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador único da transação.
     *
     * @param id O novo identificador único da transação.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém a empresa envolvida na transação.
     *
     * @return A empresa envolvida na transação.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Define a empresa envolvida na transação.
     *
     * @param empresa A nova empresa envolvida na transação.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Obtém o cliente envolvido na transação.
     *
     * @return O cliente envolvido na transação.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente envolvido na transação.
     *
     * @param cliente O novo cliente envolvido na transação.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtém o valor da transação.
     *
     * @return O valor da transação.
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define o valor da transação.
     *
     * @param valor O novo valor da transação.
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Envia callback para a empresa informando a transação.
     */
    private void enviarCallbackParaEmpresa() {
        TransacaoCallback callback = new TransacaoCallback(this.id, "Transação realizada com sucesso");

        // URL DO WebHook
        String webhookUrl = "https://webhook.site/c8eca3dc-a911-4641-84dd-41c4c0bf088e";

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, callback.toJson());

        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Callback enviado para a empresa. Resposta: " + response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Envia mensagem para o webhook informando a transação ao cliente.
     *
     * @param mensagem         A mensagem a ser enviada ao cliente.
     * @param emailSenderMock  O objeto para enviar e-mails.
     */
    /**
     * Envia mensagem para o webhook informando a transação ao cliente.
     *
     * @param mensagem        A mensagem a ser enviada ao cliente.
     * @param emailSenderMock     O objeto para enviar e-mails.
     */
    private void enviarMensagemParaWebhook(String mensagem, br.com.corporation.CorporationTest.model.EmailSender emailSenderMock) {
        // Construir objeto para enviar ao webhook
        ClienteWebhook clienteWebhook = new ClienteWebhook(this.cliente.getEmail(), mensagem);

        // Envia mensagem para o cliente via webhook
        clienteWebhook.enviarMensagemPorWebhook();

        // Envia e-mail para o cliente
        emailSenderMock.sendEmail(clienteWebhook);
    }

    public void realizarTransacao(br.com.corporation.CorporationTest.model.EmailSender emailSenderMock) {
        BigDecimal valorComTaxas = this.valor;
        for (TaxaSistema taxa : this.empresa.getTaxas()) {
            BigDecimal taxaValor = BigDecimal.valueOf(taxa.getValor()).divide(BigDecimal.valueOf(100));
            valorComTaxas = valorComTaxas.add(this.valor.multiply(taxaValor));
        }

        // Verifica se o cliente possui saldo suficiente para a transação
        if (this.cliente.getSaldo().compareTo(valorComTaxas) >= 0) {
            // Realiza a transação debitando o valor com as taxas do saldo
            this.cliente.setSaldo(this.cliente.getSaldo().subtract(valorComTaxas));
            // Adiciona a transação à lista de transações do cliente
            this.cliente.getTransacoes().add(this);

            // Envia callback para a empresa
            enviarCallbackParaEmpresa();

            // Envia mensagem para o cliente via webhook
            enviarMensagemParaWebhook("Transação realizada com sucesso. Valor: " + this.valor, emailSenderMock);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transação");
        }
    }

    /**
     * Interface para enviar e-mails.
     */
    public interface EmailSender {
        void sendEmail(String to, String subject, String message);

		void sendEmail(ClienteWebhook clienteWebhook);
    }

    /**
     * Implementação padrão de EmailSender.
     */
    public static class EmailSenderImpl implements EmailSender {
        @Override
        public void sendEmail(String to, String subject, String message) {
            // Implementação para enviar e-mail (pode ser substituída por uma integração real com serviço de e-mail)
            System.out.println("Enviando e-mail para: " + to);
            System.out.println("Assunto: " + subject);
            System.out.println("Mensagem: " + message);
        }

		@Override
		public void sendEmail(ClienteWebhook clienteWebhook) {
			// TODO Auto-generated method stub
			
		}
    }
}
