/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.aut;

import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class MeuUser extends User {
    private Usuario usuario;
    public MeuUser(Usuario usuario) {
        super(usuario.getLogin(),
                usuario.getSenha(),
                AuthorityUtils.createAuthorityList(
                    usuario.getPermissoes().toArray(new String[]{})));
        this.usuario=usuario;
    }
    public Usuario getUsuario() {
        return usuario;
    }
}
