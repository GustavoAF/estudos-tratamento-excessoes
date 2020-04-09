package com.tratamentoexcessoes.estudos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tratamentoexcessoes.estudos.domain.model.Cozinha;

/*
 * Interface do framework spring data usada para disponibilizar 
 * um crud pdrão
 */

@Repository
public interface CozinhaRepository extends CrudRepository<Cozinha, Long> {

}
