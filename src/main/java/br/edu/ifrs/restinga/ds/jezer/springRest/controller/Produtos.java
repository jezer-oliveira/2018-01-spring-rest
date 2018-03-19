/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.controller;

import br.edu.ifrs.restinga.ds.jezer.springRest.dao.ProdutoDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Produto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jezer
 */
@RestController
public class Produtos {
    
    @Autowired
    ProdutoDAO produtoDAO;
    
    @RequestMapping(path = "/produtos/", method =RequestMethod.GET)
    public Iterable<Produto> listar() {
        return produtoDAO.findAll();
    
    }
    
    @RequestMapping(path = "/produtos/{id}", method =RequestMethod.GET)
    public  Optional<Produto> recuperar(@PathVariable int id)  {
        Optional<Produto> findById = produtoDAO.findById(id);
        return  findById;
    }
    
    @RequestMapping(path="/produtos/",method = RequestMethod.POST)
    public Produto inserir(@RequestBody Produto produto) {
        produto.setId(0);
        Produto produtoComId = produtoDAO.save(produto);
        return produtoComId;
    }
    
    @RequestMapping(path="/produtos/{id}",method = RequestMethod.PUT)
    public void atualizar(@PathVariable int id, @RequestBody Produto produto) {
        produto.setId(id);
        produtoDAO.save(produto);
    }
    
    @RequestMapping(path = "/produtos/{id}", method = RequestMethod.DELETE)
    public void apagar(@PathVariable int id) {
        produtoDAO.deleteById(id);
    
    }
    
    
}
