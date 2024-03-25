package com.senac.ProjetoFotos.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SenhaRepository extends JpaRepository<SenhaEntity, Integer> {

    SenhaEntity findBySenha(String senha);
}
