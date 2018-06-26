/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.controller;

import br.edu.ifrs.restinga.ds.jezer.springRest.dao.ModeloDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.dao.ProdutoDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.jezer.springRest.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Marca;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Modelo;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Produto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
public class ProdutosPaginado {
    @Autowired
    ProdutoDAO produtoDAO;
    
    @RequestMapping(path = "/produtos/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Produto> listar(
            @RequestParam int pagina, 
            @RequestParam int tamanho,
            @RequestParam(required = false, defaultValue = "id") String ordem,
            @RequestParam(required = false, defaultValue = "ASC") String direcao
            ) {
        
        return produtoDAO.findAll(PageRequest.of(pagina, 
                tamanho,Sort.Direction.valueOf(direcao), ordem));
    }
    
    
    @Autowired
    ModeloDAO modeloDAO;

    @RequestMapping(path = "/produtos/pesquisar/nome", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Produto> pesquisarNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam int pagina, 
            @RequestParam int tamanho,
            @RequestParam(required = false, defaultValue = "id") String ordem,
            @RequestParam(required = false, defaultValue = "ASC") String direcao

            
            ) {
        if (igual != null && !igual.isEmpty()) {
            return produtoDAO.findByNome(igual,
                    PageRequest.of(pagina, tamanho,Sort.Direction.valueOf(direcao), ordem));
        } else {
            return produtoDAO.findByNomeContaining(contem,
                    PageRequest.of(pagina, tamanho,Sort.Direction.valueOf(direcao), ordem));

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
    public Produto recuperar(@PathVariable int id) {

        Optional<Produto> findById = produtoDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw  new NaoEncontrado("Não encontrado");

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
        if (produto.getValor() <= 0) {
            throw new RequisicaoInvalida("Valor deve ser maior que 0");
        }
        Optional<Produto> optionalProdutoBanco = produtoDAO.findById(id);
        if(!optionalProdutoBanco.isPresent())
            throw new NaoEncontrado("ID não encontrada");
        Produto produtoBanco = optionalProdutoBanco.get();
        produtoBanco.setNome(produto.getNome());
        produtoBanco.setValor(produto.getValor());
        produtoBanco.setFornecedor(produto.getFornecedor());
        produtoDAO.save(produtoBanco);
    }

    @RequestMapping(path = "/produtos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {

        if (!produtoDAO.existsById(id)) {
            throw new NaoEncontrado("ID não encontrada");
        }

        produtoDAO.deleteById(id);

    }

    
    @RequestMapping(path= "/produtos/{id}/marcas/", 
            method = RequestMethod.GET)
    public Iterable<Marca> recuperarMarcas(@PathVariable int id) {
        return this.recuperar(id).getMarcas();
    }

    
    @RequestMapping(path = "/produtos/{idProduto}/marcas/", 
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Marca>  inseriMarca(@PathVariable int idProduto, 
            @RequestBody Marca marca) {
        Produto produto = this.recuperar(idProduto);
        produto.getMarcas().add(marca);
        produtoDAO.save(produto);
        return produto.getMarcas();
    }
    
    @RequestMapping(path= "/produtos/{idProduto}/marcas/{id}", 
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagarMarca(@PathVariable int idProduto, 
            @PathVariable int id) {
        Marca marcaAchada=null;
        Produto produto = this.recuperar(idProduto);
        List<Marca> marcas = produto.getMarcas();
        for (Marca marcaLista : marcas) {
            if(id==marcaLista.getId())
                marcaAchada=marcaLista;
        }
        if(marcaAchada!=null) {
            produto.getMarcas().remove(marcaAchada);
            produtoDAO.save(produto);
        } else 
            throw new NaoEncontrado("Não encontrado");
    }
    
    
    @RequestMapping(path = "/produtos/{idProduto}/modelos/", 
            method = RequestMethod.GET)
    public Iterable<Modelo> listarModelo(@PathVariable int idProduto) {
        return this.recuperar(idProduto).getModelos();
    }

    
    @RequestMapping(path = "/produtos/{idProduto}/modelos/", 
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Modelo inserirModelo(@PathVariable int idProduto, 
            @RequestBody Modelo modelo) {
        modelo.setId(0);
        Modelo modeloSalvo = modeloDAO.save(modelo);
        Produto produto = this.recuperar(idProduto);
        produto.getModelos().add(modeloSalvo);
        produtoDAO.save(produto);
        return modeloSalvo;
    }

    @RequestMapping(path = "/produtos/{idProduto}/modelos/{id}", method = RequestMethod.GET)
    public Modelo recuperarModelo(@PathVariable int idProduto, @PathVariable int id) {
        Optional<Modelo> findById = modeloDAO.findById(id);
        if(findById.isPresent())
            return findById.get();
        else 
            throw new NaoEncontrado("Não encontrado");
    }
    
    @RequestMapping(path = "/produtos/{idProduto}/modelos/{id}", 
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizarModelo(@PathVariable int idProduto, @PathVariable int id, @RequestBody Modelo modelo){
        if(modeloDAO.existsById(id)){
            modelo.setId(id);
            modeloDAO.save(modelo);
        } else 
            throw new NaoEncontrado("Não encontrado");
    
    }
    
    @RequestMapping(path= "/produtos/{idProduto}/modelos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagarModelo(@PathVariable int idProduto, 
            @PathVariable int id) {
        
        Modelo modeloAchada=null;
        Produto produto = this.recuperar(idProduto);
        List<Modelo> modelos = produto.getModelos();
        for (Modelo modeloLista : modelos) {
            if(id==modeloLista.getId())
                modeloAchada=modeloLista;
        }
        if(modeloAchada!=null) {
            produto.getModelos().remove(modeloAchada);
            produtoDAO.save(produto);
        } else 
            throw new NaoEncontrado("Não encontrado");
    }


    
}
