package com.tratamentoexcessoes.estudos.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.tratamentoexcessoes.estudos.api.exception.RegistroNaoEncontradoException;
import com.tratamentoexcessoes.estudos.domain.model.Cozinha;
import com.tratamentoexcessoes.estudos.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	CozinhaService cozinhaService;
	
	//Nesse caso o tratamento de excessão esta sendo verificado, e somente isso, o retorno é um ResponseEntity, ou seja a excessao não é
	//jogada para um nivel acima, fazendo com que o Exception Handler não a capture. Como retorno no body esta sendo enviado a mensagem gerada pela 
	//excessao criada por nós.
	@DeleteMapping("/deletar1/{id}")
	public ResponseEntity<Object> deletar1(@PathVariable Long id){
		
		try {
			cozinhaService.deletar1(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			
		}catch (RegistroNaoEncontradoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	//Nesse caso o tratamento de excessao é realizado no controlador, sendo que a excessao é gerada na classe de serviço, esta detecta uma exception nativa
	//e cria uma exception personalizada jogando para cima atraves do throw. O controlador recebendo essa exception verifica se é conhecida
	//caso seja, retorna uma classe nativa do Spring chanda ResponseStatusException, esta é capturada pelo nosso Exception Handler, no Handler existe
	//o tratamento para a classe RegistroNaoEncontradoException, mas porque o Handler à captura sendo que sao exceptions diferentes?
	//Porque RegistroNaoEncontradoException herda de RuntimeException, e ResponseStatusException herda de NestedRuntimeException que este tambem herda de 
	//RuntimeException. Esta exception não é muito indicada uma vez que retorna o trace, e isso não é uma boa prática.
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/deletar2/{id}")
	public void deletar2(@PathVariable Long id){
		
		try {
			cozinhaService.deletar2(id);
			
		}catch (RegistroNaoEncontradoException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	
	//Nesse outro modelo de tratamento de excessao não é feito no controlador, e sim recebido diretamente da camada de serviço. Esta retorna uma exception 
	//personalizada RegistroNaoEncontradoException, como o método mapeado é um void, a exception e convertida para o http, sendo capturada pelo Handler
	//que trata exatamente essa exception. Retornando assim o objeto criado para retornos de excessão.
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/deletar3/{id}")
	public void deletar3(@PathVariable Long id){
		
		cozinhaService.deletar3(id);
	}
	
	//Recurso testando exceptions na url, podendo ser tanto incompatibilidade de tipos como erro na url devolvendo uma mensagem
	//de recurso não encontrado.
	@GetMapping
	public ResponseEntity<List<Cozinha>> pbter(){
		
		return ResponseEntity.status(HttpStatus.OK).body(cozinhaService.obter());
	}
	
	//Recurso usado para testar erros no corpo da requisição, nenhum tratamento de exceptions esta sendo feito, tanto no service como no 
	//controler, mas mesmo assim o ExceptionHandler captura a excessão
	@PostMapping
	public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaService.salva(cozinha));
	}
}
