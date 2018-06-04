/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.controller;

import br.edu.ifrs.restinga.ds.jezer.springRest.dao.FornecedorDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Fornecedor;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jezer
 */
@RestController
@RequestMapping(path = "/api")
public class Fornecedores {

    @Autowired
    FornecedorDAO fornecedorDAO;

    @RequestMapping(path = "/fornecedores", method = RequestMethod.GET)
    public Iterable<Fornecedor> listar() {
        return fornecedorDAO.findAll();
    }

    @RequestMapping(path = "/fornecedores", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Fornecedor inserir(@RequestBody Fornecedor fornecedor) {
        fornecedor.setId(0);
        Fornecedor fornecedorSalvo = fornecedorDAO.save(fornecedor);
        return fornecedorSalvo;
    }

    @RequestMapping(path = "/fornecedores/{id}", method = RequestMethod.GET)
    public Fornecedor recuperar(@PathVariable int id) {
        Optional<Fornecedor> findById = fornecedorDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/fornecedores/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Fornecedor fornecedor) {
        if (fornecedorDAO.existsById(id)) {
            fornecedor.setId(id);
            fornecedorDAO.save(fornecedor);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/fornecedores/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (fornecedorDAO.existsById(id)) {
            fornecedorDAO.deleteById(id);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

}
