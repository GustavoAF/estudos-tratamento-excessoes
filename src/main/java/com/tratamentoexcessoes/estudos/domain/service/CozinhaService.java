package com.tratamentoexcessoes.estudos.domain.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.tratamentoexcessoes.estudos.api.exception.RegistroNaoEncontradoException;
import com.tratamentoexcessoes.estudos.domain.model.Cozinha;
import com.tratamentoexcessoes.estudos.domain.repository.CozinhaRepository;

/*
 * Classe de serviço, contém os métodos que serão chamados pelo controlador,
 * sua função além de organizar as camadas, é identificar uma excessão nativa do spring
 * trata-la e gerar uma nova invocando a exception personalizada. 
 */

@Service
public class CozinhaService {
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	public void deletar1(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new RegistroNaoEncontradoException(String.format("Não existe um cadastro de cozinha com código %d", id));
		}
	}

	public void deletar2(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new RegistroNaoEncontradoException(String.format("Não existe um cadastro de cozinha com código %d", id));
		}		
	}
	
	public void deletar3(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new RegistroNaoEncontradoException(String.format("Não existe um cadastro de cozinha com código %d", id));
		}		
	}
	
	public void deletar4(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new RegistroNaoEncontradoException(String.format("Não existe um cadastro de cozinha com código %d", id));
		}		
	}
	
	public List <Cozinha> obter() {		
		return (List<Cozinha>) cozinhaRepository.findAll();
	}
	
	public Cozinha salva(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);	
	}
}
