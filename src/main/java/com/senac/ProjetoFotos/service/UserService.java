package com.senac.ProjetoFotos.service;

import com.senac.ProjetoFotos.data.UserEntity;
import com.senac.ProjetoFotos.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity criarUsuario(UserEntity user) {
        UserEntity newUser = new UserEntity();
        newUser.setSenha(user.getSenha());
        newUser.setLogin(user.getLogin());
        newUser.setId(null);
        userRepository.save(newUser);
        return user;
    }

    public boolean autenticar(String login, String senha) {
        UserEntity user = userRepository.findByLogin(login);
        if (user != null && user.getLogin() == login && user.getSenha() == senha) {
            return true;
        } else {
            return false;

        }
    }

    public UserEntity buscarPorLogin(String login) {
        UserEntity user = userRepository.findByLogin(login);
        return user;
    }
}
