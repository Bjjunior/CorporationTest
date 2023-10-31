package br.com.corporation.CorporationTest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

/**
 * Representa uma entidade Empresa no sistema.
 * 
 * 
 * Esta classe é mapeada para a tabela de empresas no banco de dados.
 * 
 * 
 * @version 1.0
 */

@Entity
@SequenceGenerator(name = "empresa", sequenceName = "SQ_T_EMPRESA", allocationSize = 1)
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "empresa")
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cnpj;

    private String endereco;

    private String segmento;

    private BigDecimal saldo;

    // Adicionado campo para referenciar as taxas de sistema associadas à empresa
    @OneToMany(mappedBy = "empresa")
    private List<TaxaSistema> taxas = new ArrayList<>();

    // Construtor
    public Empresa() {
    }

    // Construtor com parâmetros
    public Empresa(String nome, String cnpj, String endereco, String segmento, BigDecimal saldo) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.segmento = segmento;
        this.saldo = saldo;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    // Método para validar o CNPJ
    public void validarCNPJ() {
        // Implementação da lógica de validação do CNPJ usando a classe CnpjValidator
        if (!CnpjValidador.isValid(this.cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
    }

    public List<TaxaSistema> getTaxas() {
        return taxas;
    }
}
