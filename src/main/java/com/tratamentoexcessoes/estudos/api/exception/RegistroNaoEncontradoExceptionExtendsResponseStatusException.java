package com.tratamentoexcessoes.estudos.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


/*
 *  Classe de exception personalizada, extendendo da Classe ResponseStatusException.
 * 	Criada a partir do spring 5 a classe ResponseStatusException serve como exception base,
 *  é de responsabilidade dessa, fazer varios tratamentos necessários como código http de resposta,
 *  personalizar a mensagem exibida, além de retornoa o stack trace do spring. 
 *  Várias classes nativas do spring herdam dessa classe, especializando a para cada tipo de código http de erro.
 *  Uma boa funcionalidade dessa classe seria, herdar como feito nessa classe (RegistroNaoEncontradoExceptionExtendsResponseStatusException)
 *  e invocar o construtor super da classe pai passando o código http e a mensagem como parâmetro.
 *  Dessa forma podemos personalizar nossas respostas de retorno, já que todas as informações da ResponseStatusException
 *  são geradas em Inglês.
 */
public class RegistroNaoEncontradoExceptionExtendsResponseStatusException extends ResponseStatusException {
	
	private static final long serialVersionUID = 1L;
	
	public RegistroNaoEncontradoExceptionExtendsResponseStatusException(HttpStatus status, String mensagem) {
		super(status,mensagem);
	}
	

	public RegistroNaoEncontradoExceptionExtendsResponseStatusException(String mensagem) {
		super(HttpStatus.NOT_FOUND,mensagem);
	}

}
