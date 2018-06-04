package br.edu.ifrs.restinga.ds.jezer.springRest.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.edu.ifrs.restinga.ds.jezer.springRest.dao.PessoaDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Pessoa;
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
@RequestMapping("/api")
public class Pessoas {
    
    @Autowired
    PessoaDAO pessoaDAO; 
    
    @RequestMapping(path = "/pessoas/", method = RequestMethod.GET)
    public Iterable<Pessoa> listar() {
        return pessoaDAO.findAll();
    }

    
    @RequestMapping(path = "/pessoas/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa inserir(@RequestBody Pessoa pessoa) {
        pessoa.setId(0);
        Pessoa pessoaSalvo = pessoaDAO.save(pessoa);
        return pessoaSalvo;
    }

    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.GET)
    public Pessoa recuperar(@PathVariable int id) {
        return pessoaDAO.findById(id).get();
    }
    
    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Pessoa pessoa){
        if(pessoaDAO.existsById(id)) {
            pessoa.setId(id);
            pessoaDAO.save(pessoa);
        }
    
    }
    
    @RequestMapping(path= "/pessoas/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if(pessoaDAO.existsById(id)) {
            pessoaDAO.deleteById(id);
        }
        
    }

}
