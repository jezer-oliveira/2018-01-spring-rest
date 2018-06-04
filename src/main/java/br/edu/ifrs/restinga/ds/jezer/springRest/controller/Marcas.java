/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.controller;

import br.edu.ifrs.restinga.ds.jezer.springRest.dao.MarcaDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Marca;
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
public class Marcas {
    
    @Autowired
    MarcaDAO marcaDAO; 
    
    @RequestMapping(path = "/marcas", method = RequestMethod.GET)
    public Iterable<Marca> listar() {
        return marcaDAO.findAll();
    }

    
    @RequestMapping(path = "/marcas", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Marca inserir(@RequestBody Marca marca) {
        marca.setId(0);
        Marca marcaSalvo = marcaDAO.save(marca);
        return marcaSalvo;
    }

    @RequestMapping(path = "/marcas/{id}", method = RequestMethod.GET)
    public Marca recuperar(@PathVariable int id) {
        Optional<Marca> findById = marcaDAO.findById(id);
        if(findById.isPresent())
            return findById.get();
         else 
            throw new NaoEncontrado("Não encontrado");
    }
    
    @RequestMapping(path = "/marcas/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Marca marca){
        if(marcaDAO.existsById(id)){
            marca.setId(id);
            marcaDAO.save(marca);
        } else 
            throw new NaoEncontrado("Não encontrado");
    
    }
    
    @RequestMapping(path= "/marcas/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if(marcaDAO.existsById(id)){
            marcaDAO.deleteById(id);
        } else 
            throw new NaoEncontrado("Não encontrado");
        
    }

}
