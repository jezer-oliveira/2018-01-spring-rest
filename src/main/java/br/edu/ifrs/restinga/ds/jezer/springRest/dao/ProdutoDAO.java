/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.dao;

import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jezer
 */
@Repository
public interface ProdutoDAO extends CrudRepository<Produto, Integer>{
    Iterable<Produto> findByNome(String nome); 
    Iterable<Produto> findByNomeContaining(String nome);
    
    Iterable<Produto> findByValor(float valor);
    Iterable<Produto> findByValorGreaterThanEqual(float valor);
    public Iterable<Produto> findByValorLessThanEqual(Float menor);

    public Iterable<Produto> findByValorBetween(Float a1, Float a2);
    public Iterable<Produto> findByValorLessThanEqualAndValorGreaterThanEqual(
    Float menor, Float maior);
}
