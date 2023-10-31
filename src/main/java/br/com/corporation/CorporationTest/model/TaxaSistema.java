package br.com.corporation.CorporationTest.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

/**
 * Classe que representa uma taxa de sistema associada a uma empresa.
 *
 * Esta classe define o tipo de taxa, como "TAXA_DEPOSITO" ou "TAXA_SAQUE",
 * e o valor que representa o percentual da taxa, por exemplo, 2.1 para 2.1%.
 *
 * @version 1.0
 */
@Entity
@SequenceGenerator(name = "taxa_sistema", sequenceName = "SQ_T_TAXA_SISTEMA", allocationSize = 1)
public class TaxaSistema {

    /**
     * Identificador único da taxa de sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "taxa_sistema")
    private Long id;

    /**
     * Tipo da taxa de sistema, como "TAXA_DEPOSITO" ou "TAXA_SAQUE".
     */
    @NotBlank
    private String tipo;

    /**
     * Valor da taxa de sistema, representando o percentual (ex: 2.1 para 2.1%).
     */
    @NotNull
    private double valor;

    /**
     * Referência à empresa associada à taxa de sistema.
     */
    @ManyToOne
    @JoinColumn(name = "empresa_id")  // Ajuste o nome da coluna conforme necessário
    private Empresa empresa;

    /**
     * Construtor padrão da classe.
     */
    public TaxaSistema() {
    }

    /**
     * Obtém o identificador único da taxa de sistema.
     *
     * @return O identificador único da taxa de sistema.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador único da taxa de sistema.
     *
     * @param id O novo identificador único da taxa de sistema.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o tipo da taxa de sistema.
     *
     * @return O tipo da taxa de sistema.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo da taxa de sistema.
     *
     * @param tipo O novo tipo da taxa de sistema.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtém o valor da taxa de sistema.
     *
     * @return O valor da taxa de sistema.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor da taxa de sistema.
     *
     * @param valor O novo valor da taxa de sistema.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Obtém a empresa associada à taxa de sistema.
     *
     * @return A empresa associada à taxa de sistema.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Define a empresa associada à taxa de sistema.
     *
     * @param empresa A nova empresa associada à taxa de sistema.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
