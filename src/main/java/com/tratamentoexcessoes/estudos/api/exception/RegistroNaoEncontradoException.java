package com.tratamentoexcessoes.estudos.api.exception;

/*
 * classe de exception criada para tratar a excessão de forma personalidade
 * extendendo da classe RuntimeException
 */
public class RegistroNaoEncontradoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public RegistroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
