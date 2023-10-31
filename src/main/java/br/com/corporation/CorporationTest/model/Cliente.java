package br.com.corporation.CorporationTest.model;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

/**
 * Representa uma entidade Cliente no sistema.
 *
 * Esta classe é mapeada para a tabela de clientes no banco de dados.
 *
 * @version 1.0
 */
@Entity
@SequenceGenerator(name = "cliente", sequenceName = "SQ_T_CLIENTE", allocationSize = 1)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cliente")
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;

    private String endereco;

    private BigDecimal saldo;

    // Lista para armazenar as transações do cliente
    @OneToMany(mappedBy = "cliente")
    private List<Transacao> transacoes = new ArrayList<>();

    // Adicionado campo para referenciar a empresa associada ao cliente
    @ManyToOne
    private Empresa empresa;

    // Adicionado campo para o e-mail do cliente
    @NotBlank
    @Email(message = "E-mail inválido")
    private String email;

    // Construtor
    public Cliente() {
    }

    // Construtor com parâmetros
    public Cliente(String nome, String cpf, String endereco, BigDecimal saldo, Empresa empresa, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.saldo = saldo;
        this.empresa = empresa;
        this.email = email;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Realiza uma transação, considerando as taxas do sistema.
     *
     * @param transacao A transação a ser realizada.
     * @throws IllegalArgumentException Se o saldo for insuficiente para a transação.
     */
    public void realizarTransacao(Transacao transacao) {
        BigDecimal valorComTaxas = transacao.getValor();
        for (TaxaSistema taxa : transacao.getEmpresa().getTaxas()) {
            BigDecimal taxaCalculada = transacao.getValor().multiply(new BigDecimal(taxa.getValor()).divide(new BigDecimal(100)));
            valorComTaxas = valorComTaxas.add(taxaCalculada);
        }

        // Verifica se o cliente possui saldo suficiente para a transação
        if (this.saldo.compareTo(valorComTaxas) >= 0) {
            // Realiza a transação debitando o valor com as taxas do saldo
            this.saldo = this.saldo.subtract(valorComTaxas);
            // Adiciona a transação à lista
            transacoes.add(transacao);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transação");
        }
    }

    /**
     * Valida o CPF do cliente.
     *
     * @throws IllegalArgumentException Se o CPF for inválido.
     */
    public void validarCPF() {
        // Implementação da lógica de validação do CPF usando a classe CpfValidador
        if (!CpfValidador.isValid(this.cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

}
