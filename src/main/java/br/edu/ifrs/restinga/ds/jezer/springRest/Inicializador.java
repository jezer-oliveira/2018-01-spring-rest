/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.jezer.springRest;

import static br.edu.ifrs.restinga.ds.jezer.springRest.controller.Usuarios.PASSWORD_ENCODER;
import br.edu.ifrs.restinga.ds.jezer.springRest.dao.UsuarioDAO;
import br.edu.ifrs.restinga.ds.jezer.springRest.modelo.Usuario;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Inicializador {
    @Autowired
    UsuarioDAO usuarioDAO;
    // Executa o método logo após a aplicação spring inicializar por completo 
    @PostConstruct
    public void init() {
        Usuario usuarioRoot = usuarioDAO.findByLogin("admin");
        if (usuarioRoot == null) {
            usuarioRoot = new Usuario();
            usuarioRoot.setNome("admin");
            usuarioRoot.setLogin("admin");
            usuarioRoot.setSenha(PASSWORD_ENCODER.encode("12345"));
            usuarioRoot.setPermissoes(Arrays.asList("administrador" ));
            usuarioDAO.save(usuarioRoot);
        }
    }
}
