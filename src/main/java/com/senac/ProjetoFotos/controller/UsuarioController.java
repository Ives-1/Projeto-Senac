package com.senac.ProjetoFotos.controller;

import com.senac.ProjetoFotos.data.UserEntity;
import com.senac.ProjetoFotos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    UserService userService;
    
    

    @GetMapping("/inicio")
    public String inicioFotos(Model model) {

        model.addAttribute("user", new UserEntity());
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam("login") String login, @RequestParam("senha") String senha, Model model) {

        UserEntity user = userService.buscarPorLogin(login);
        try {
            if (userService.autenticar(user.getLogin(), user.getSenha())) {
                model.addAttribute("user", user);
                return "carregaFotos";
            } else {
                model.addAttribute("error", "Nome de usuário ou senha inválidos");
                return "redirect:/inicio";
            }
        } catch (Exception e) {
            return "redirect:/inicio";
        }
        
    }

}
