package com.tratamentoexcessoes.estudos.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.tratamentoexcessoes.estudos.domain.service.CozinhaService;

@RequestMapping("/exceptionhandler")
@RestController
public class CozinhaControllerExceptionHandler {
	
	@Autowired
	CozinhaService cozinhaService;
	
	
	//Este método foi criado para mostrar que um método anotado com @ExceptionHandler pode controlar todas as exceptions de um determinado controlador.
	//Pode-se ter quantos metodos com @ExceptionHandler forem necessarios, para cada exception mas também é possível indicar mais de uma classe exception
	//na anotação.
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/deletar4/{id}")
	public void deletar4(@PathVariable Long id){
		
		cozinhaService.deletar4(id);
	}
	

	//Este ExceptionHandler foi comentado mas foi desenvolvido para testar a opção de tratar as excpetions em cada controlador,
	//sem usar um opção de um exceptionhandler universal. 
/*	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<?> tratamentoRegistroNaoEncontradoException(
			RegistroNaoEncontradoException e){
		ApiError apiError = ApiError.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(apiError);	
	}
*/	
	
}
