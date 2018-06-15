/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest.aut;


import br.edu.ifrs.restinga.ds.jezer.springRest.controller.Usuarios;
import br.edu.ifrs.restinga.ds.jezer.springRest.dao.UsuarioDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MeuUserDetailsService implements UserDetailsService {
    @Autowired
    UsuarioDAO usuarioDAO;
    @Override
    public UserDetails loadUserByUsername(String login) 
            throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByLogin(login);
        System.out.println("br.edu.ifrs.restinga.ds.jezer.springRest.aut.MeuUserDetailsService.loadUserByUsername()");
        System.out.println("aqui:"+login);
        if (usuario == null) {
            throw new UsernameNotFoundException(login + " não existe!");
        }
        System.out.println("user:"+usuario.getNome());
        try {
        MeuUser meuUser = new MeuUser(usuario);    
        return meuUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
                return null;
        
    }
}
