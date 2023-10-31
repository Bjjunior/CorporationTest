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

import br.com.corporation.CorporationTest.model.Cliente;
import br.com.corporation.CorporationTest.repository.ClienteRepository;

/**
 * Controlador REST para manipulação de clientes.
 *
 * @version 1.0
 */
@RestController
@RequestMapping("cliente")
public class ClienteResource {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Lista todos os clientes.
     *
     * @return Lista de clientes cadastrados.
     */
    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    /**
     * Busca um cliente pelo código.
     *
     * @param codigo O código do cliente a ser buscado.
     * @return O cliente encontrado.
     */
    @GetMapping("{codigo}")
    public Cliente buscar(@PathVariable int codigo) {
        return clienteRepository.findById(codigo).orElse(null);
    }

    /**
     * Cadastra um novo cliente.
     *
     * @param cliente O cliente a ser cadastrado.
     * @return O cliente cadastrado.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param cliente O cliente com os dados atualizados.
     * @param id      O ID do cliente a ser atualizado.
     * @return O cliente atualizado.
     */
    @PutMapping("{id}")
    public Cliente atualizar(@RequestBody Cliente cliente, @PathVariable Long id) {
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    /**
     * Remove um cliente pelo código.
     *
     * @param codigo O código do cliente a ser removido.
     */
    @DeleteMapping("{codigo}")
    public void remover(@PathVariable int codigo) {
        clienteRepository.deleteById(codigo);
    }

    /**
     * Busca clientes por nome e/ou CPF.
     *
     * @param nome Parâmetro opcional para busca por nome.
     * @param cpf  Parâmetro opcional para busca por CPF.
     * @return Lista de clientes encontrados.
     */
    @GetMapping("pesquisa")
    public List<Cliente> buscar(@RequestParam(required = false) String nome, @RequestParam(defaultValue = "false") String cpf) {
        return nome != null ? clienteRepository.findByNomeAndCpf(nome, cpf) : clienteRepository.findByCpf(cpf);
    }
}
