package com.tratamentoexcessoes.estudos.api.exception;


import lombok.Getter;

//Enum criado para facilitar a instanciação da classe ApiError, informando o tipo do erro com o titulo e a url da documentação

@Getter
public enum ApiErrorType {
	
	REGISTRO_NAO_ENCONTRADO("/entidade-nao-encontrada","Entidade não Encontrada"),
	CORPO_FORA_PADRAO("/corpo-mensagem-fora-padrao","Corpo da mensagem fora do padrão"),
	CAMPOS_FORA_PADRAO("/campos-mensagem-fora-padra","Campos fora padrão"),
	CAMPOS_DESCONHECIDOS("/campos-desconhecidos","Campos desconhecidos"),
	PARAMETRO_URL_INCORRETO("/parametro-uri-inválido","Parâmetro url inválido"),
	RECURSO_NAO_ENCONTRATO("/recurso-nao-encontrato","Recurso não encontrado") ;
	
	private String title;
	private String path;
	
	ApiErrorType(String path, String title){
		this.path = "Https://meusite.com.br/api/documentacao" + path;
	    this.title = title;
		
	}

}
