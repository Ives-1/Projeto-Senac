package com.senac.ProjetoFotos.controller;

import com.senac.ProjetoFotos.data.UserEntity;
import com.senac.ProjetoFotos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    UserService userService;
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/inicio")
    public String inicioFotos(Model model){
        //criando um user para testes
        UserEntity user = new UserEntity();
        user.setLogin("Ives");
        user.setSenha("123");
        userService.criarUsuario(user);
        
        model.addAttribute("user", new UserEntity());
        return "index";
    }
    
    @PostMapping("/Login")
    public String login(@RequestParam("login") String login, @RequestParam("senha") String senha, Model model){
        
    UserEntity user = userService.buscarPorLogin(login);
        if (user != null && passwordEncoder.matches(senha, user.getSenha())) {
            model.addAttribute("user", user);
            return "carregaFotos";
        } else {
            model.addAttribute("error", "Nome de usuário ou senha inválidos");
            return "index";
        }
    }
    
    
    
}
