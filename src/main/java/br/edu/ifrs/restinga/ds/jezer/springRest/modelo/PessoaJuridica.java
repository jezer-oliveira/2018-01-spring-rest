/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 *
 * @author jezer
 */

@Entity
public class PessoaJuridica extends Pessoa {
    
    // para não gravar no banco 
    @Transient
    // Define o campo
    @JsonProperty("tipo")
    private final String tipo = "juridica";
    
    private String cnpj;
    private String nomeFantasia;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
   
}
