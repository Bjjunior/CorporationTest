package br.com.corporation.CorporationTest.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.corporation.CorporationTest.model.Empresa;
import br.com.corporation.CorporationTest.repository.EmpresaRepository;

/**
 * Controlador REST para manipulação de empresas.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("empresa")
public class EmpresaResource {

    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Lista todas as empresas.
     *
     * @return Lista de empresas cadastradas.
     */
    @GetMapping
    public List<Empresa> listar() {
        return empresaRepository.findAll();
    }

    /**
     * Busca uma empresa pelo código.
     *
     * @param codigo O código da empresa a ser buscada.
     * @return A empresa encontrada.
     */
    @GetMapping("{codigo}")
    public Empresa buscar(@PathVariable int codigo) {
        return empresaRepository.findById(codigo).orElse(null);
    }

    /**
     * Cadastra uma nova empresa.
     *
     * @param empresa A empresa a ser cadastrada.
     * @return A empresa cadastrada.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Empresa cadastrar(@RequestBody Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    /**
     * Atualiza uma empresa existente.
     *
     * @param empresa A empresa com os dados atualizados.
     * @param id      O ID da empresa a ser atualizada.
     * @return A empresa atualizada.
     */
    @PutMapping("{id}")
    public Empresa atualizar(@RequestBody Empresa empresa, @PathVariable Long id) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    /**
     * Remove uma empresa pelo código.
     *
     * @param codigo O código da empresa a ser removida.
     */
    @DeleteMapping("{codigo}")
    public void remover(@PathVariable int codigo) {
        empresaRepository.deleteById(codigo);
    }

    /**
     * Busca empresas por nome e/ou CNPJ.
     *
     * @param nome Parâmetro opcional para busca por nome.
     * @param cnpj Parâmetro opcional para busca por CNPJ.
     * @return Lista de empresas encontradas.
     */
    @GetMapping("pesquisa")
    public List<Empresa> buscar(@RequestParam(required = false) String nome, @RequestParam(defaultValue = "false") String cnpj) {
        return nome != null ? empresaRepository.findByNomeAndCnpj(nome, cnpj) : empresaRepository.findByCnpj(cnpj);
    }
}
