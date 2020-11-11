
package com.aline.endpoints;

import com.aline.Controller.ContaDAO;
import com.aline.Model.Conta;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginHandler {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Conta requestLogin(@RequestHeader(value = "login", defaultValue = "") String login, @RequestHeader(value = "senha", defaultValue = "") String senha) {
        return ContaDAO.findConta(login, senha);
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public void cadastraLogin (@RequestHeader(value = "nome", defaultValue = "") String nome, @RequestHeader(value = "login", defaultValue = "") String login, @RequestHeader(value = "senha", defaultValue = "") String senha) {
        Conta c = new Conta();
        c.setNome(nome);
        c.setLogin(login);
        c.setSenha(senha);
        ContaDAO.createConta(c);
    }

}








