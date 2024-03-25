package com.senac.ProjetoFotos.service;

import com.senac.ProjetoFotos.data.SenhaEntity;
import com.senac.ProjetoFotos.data.SenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenhaService {

    @Autowired
    SenhaRepository senhaRepository;

    public boolean autentica(String senha) {

        try {
            SenhaEntity senhaEntity = senhaRepository.findBySenha(senha);
            if (senhaEntity.getSenha() == senha) {
                return true;

            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
