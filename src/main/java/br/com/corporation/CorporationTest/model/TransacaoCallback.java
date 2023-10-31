package br.com.corporation.CorporationTest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe que representa um callback de transação enviado para a empresa.
 * 
 * Esta classe contém informações sobre a transação, como o identificador único da transação
 * e uma mensagem associada ao callback.
 * 
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransacaoCallback {

    /**
     * Identificador único da transação associada ao callback.
     */
    @JsonProperty("transacaoId")
    private Long transacaoId;

    /**
     * Mensagem associada ao callback.
     */
    @JsonProperty("mensagem")
    private String mensagem;

    /**
     * Construtor padrão da classe.
     */
    public TransacaoCallback() {
    }

    /**
     * Construtor com parâmetros para inicializar um callback de transação.
     * 
     * @param transacaoId O identificador único da transação associada ao callback.
     * @param mensagem    A mensagem associada ao callback.
     */
    public TransacaoCallback(Long transacaoId, String mensagem) {
        this.transacaoId = transacaoId;
        this.mensagem = mensagem;
    }

    /**
     * Obtém o identificador único da transação associada ao callback.
     * 
     * @return O identificador único da transação associada ao callback.
     */
    public Long getTransacaoId() {
        return transacaoId;
    }

    /**
     * Define o identificador único da transação associada ao callback.
     * 
     * @param transacaoId O novo identificador único da transação associada ao callback.
     */
    public void setTransacaoId(Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    /**
     * Obtém a mensagem associada ao callback.
     * 
     * @return A mensagem associada ao callback.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Define a mensagem associada ao callback.
     * 
     * @param mensagem A nova mensagem associada ao callback.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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
