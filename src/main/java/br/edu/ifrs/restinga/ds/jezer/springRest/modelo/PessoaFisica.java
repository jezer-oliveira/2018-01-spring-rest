/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author jezer
 */

@Entity
public class PessoaFisica extends Pessoa {
    
    // Para não gravar no banco 
    @Transient
    // Define o campo
    @JsonProperty("tipo")
    private final String tipo = "fisica";
    
    private String sexo;
    private String cpf;
    
    @Temporal(TemporalType.DATE)
    private Date nascimento;

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }
    
}
