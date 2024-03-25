package com.senac.ProjetoFotos.service;

import com.senac.ProjetoFotos.data.UserEntity;
import com.senac.ProjetoFotos.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    public UserEntity criarUsuario(UserEntity user){
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        user.setId(null);
        userRepository.save(user);
        return user;
    }
    
    public UserEntity autenticar(String login, String senha){
        UserEntity user = userRepository.findByLogin(login);
        if (user != null && passwordEncoder.matches(senha, user.getSenha())) {
            return user;
        }else{
            throw new BadCredentialsException("Login ou Senha Está Incorreta");
        }
    }
    
    public UserEntity buscarPorLogin(String login){
        UserEntity user = userRepository.findByLogin(login);
        return user;
    }
}
