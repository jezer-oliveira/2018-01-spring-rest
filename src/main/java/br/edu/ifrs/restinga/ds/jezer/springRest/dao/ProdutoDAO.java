/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.dao;

import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Produto;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jezer
 */
@Repository
public interface ProdutoDAO extends PagingAndSortingRepository<Produto, Integer>{
    List<Produto> findByNomeOrderByValor(String nome);

    public Iterable<Produto> findByNomeContaining(String contem);

    public Iterable<Produto> findByNome(String igual);
    
    public Iterable<Produto> findByValidadeGreaterThan(Date date);
    
    
}
