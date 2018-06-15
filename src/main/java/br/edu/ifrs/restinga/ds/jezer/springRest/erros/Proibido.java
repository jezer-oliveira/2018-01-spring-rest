/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.erros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author jezer
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class Proibido extends RuntimeException {
    public Proibido(String erro) {
        super(erro);
    }
    
}
