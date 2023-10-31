package br.com.corporation.CorporationTest.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClienteWebhook {

    private String email;
    private String mensagem;

    public ClienteWebhook(String email, String mensagem) {
        this.email = email;
        this.mensagem = mensagem;
    }

    // Adicione este método para enviar a mensagem por webhook
    public void enviarMensagemPorWebhook() {
        // Implementação para enviar mensagem por webhook (pode ser um HTTP POST, por exemplo)
        System.out.println("Enviando mensagem por webhook para: " + email);
        System.out.println("Mensagem: " + mensagem);
    }

    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter objeto para JSON", e);
        }
    }
}
