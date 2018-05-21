/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.controller;

import br.edu.ifrs.restinga.ds.jezer.springRest.dao.ProdutoDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.jezer.springRest.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Produto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jezer
 */
@RestController
@RequestMapping("/api")
public class Produtos {
    
    @RequestMapping(path = "/produtos/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Produto> listar() {
        return produtoDAO.findAll();
    }
    
    
    @Autowired
    ProdutoDAO produtoDAO;

    @RequestMapping(path = "/produtos/pesquisar/nome", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Produto> pesquisarNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem) {
        if (igual != null && !igual.isEmpty()) {
            return produtoDAO.findByNome(igual);
        } else {
            return produtoDAO.findByNomeContaining(contem);

        }
    }

    @RequestMapping(path = "/produtos/pesquisar/valor", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Produto> pesquisarValor(
            @RequestParam(required = false) Float igual,
            @RequestParam(required = false) Float intervaloInicial,
            @RequestParam(required = false) Float intervaloFinal) {
        if(igual!=null) {
            return produtoDAO.findByValor(igual);
        } else if(intervaloInicial!=null&&intervaloFinal!=null) {
            System.out.println("Aqui: maior:"+intervaloInicial+" menor:"+intervaloFinal);
            return produtoDAO.findByValorBetween(intervaloInicial, intervaloFinal);
        } else if(intervaloInicial!=null) {
            return produtoDAO.findByValorGreaterThanEqual(intervaloInicial);
        } else if(intervaloFinal!=null){
            return produtoDAO.findByValorLessThanEqual(intervaloFinal);
        } else {
            throw new RequisicaoInvalida("Erro: informar igual ou maior");
        }
    }

    
    @RequestMapping(path = "/produtos/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Produto> recuperar(@PathVariable int id) {

        Optional<Produto> findById = produtoDAO.findById(id);
        if (findById.isPresent()) {
            return ResponseEntity.ok(findById.get());
        } else {
            return ResponseEntity.notFound().build();

        }
    }

    @RequestMapping(path = "/produtos/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Produto inserir(@RequestBody Produto produto) {
        produto.setId(0);

        if (produto.getValor() <= 0) {
            throw new RequisicaoInvalida("Valor deve ser maior que 0");
        }

        Produto produtoComId = produtoDAO.save(produto);
        return produtoComId;
    }

    @RequestMapping(path = "/produtos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable int id, @RequestBody Produto produto) {
        produto.setId(id);
        if (produto.getValor() <= 0) {
            throw new RequisicaoInvalida("Valor deve ser maior que 0");
        }
        produtoDAO.save(produto);
    }

    @RequestMapping(path = "/produtos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {

        if (!produtoDAO.existsById(id)) {
            throw new NaoEncontrado("ID não encontrada");
        }

        produtoDAO.deleteById(id);

    }

}
