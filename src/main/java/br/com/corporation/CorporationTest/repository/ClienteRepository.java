package br.com.corporation.CorporationTest.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.corporation.CorporationTest.model.Cliente;

/**
 * Interface que define as operações de acesso a dados para a entidade Cliente.
 * Utiliza o Spring Data JPA para simplificar a interação com o banco de dados.
 * 
 * @version 1.0
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /**
     * Busca uma lista de clientes pelo nome.
     * 
     * @param nome O nome a ser utilizado como critério de busca.
     * @return Uma lista de clientes encontrados pelo nome.
     */
    List<Cliente> findByNome(String nome);

    /**
     * Busca uma lista de clientes pelo CPF.
     * 
     * @param cpf O CPF a ser utilizado como critério de busca.
     * @return Uma lista de clientes encontrados pelo CPF.
     */
    List<Cliente> findByCpf(String cpf);

    /**
     * Busca uma lista de clientes pelo nome e CPF.
     * 
     * @param nome O nome a ser utilizado como critério de busca.
     * @param cpf  O CPF a ser utilizado como critério de busca.
     * @return Uma lista de clientes encontrados pelo nome e CPF.
     */
    List<Cliente> findByNomeAndCpf(String nome, String cpf);
}
