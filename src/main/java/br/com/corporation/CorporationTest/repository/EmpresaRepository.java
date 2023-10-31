package br.com.corporation.CorporationTest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.corporation.CorporationTest.model.Empresa;

/**
 * Interface que define as operações de acesso a dados para a entidade Empresa.
 * Utiliza o Spring Data JPA para simplificar a interação com o banco de dados.
 * 
 * @version 1.0
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    /**
     * Busca uma lista de empresas pelo nome.
     * 
     * @param nome O nome a ser utilizado como critério de busca.
     * @return Uma lista de empresas encontradas pelo nome.
     */
    List<Empresa> findByNome(String nome);

    /**
     * Busca uma lista de empresas pelo CNPJ.
     * 
     * @param cnpj O CNPJ a ser utilizado como critério de busca.
     * @return Uma lista de empresas encontradas pelo CNPJ.
     */
    List<Empresa> findByCnpj(String cnpj);

    /**
     * Busca uma lista de empresas pelo nome e CNPJ.
     * 
     * @param nome O nome a ser utilizado como critério de busca.
     * @param cnpj O CNPJ a ser utilizado como critério de busca.
     * @return Uma lista de empresas encontradas pelo nome e CNPJ.
     */
    List<Empresa> findByNomeAndCnpj(String nome, String cnpj);
}
