package com.tratamentoexcessoes.estudos.infrastructure.repository.applicationproperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/*
 * Classe usada para obter as informações de variáveis de ambiente
 */

@Component
@Data
public class ConfigurationsBd {
	
	@Value("${url_banco}")
	private String dataSourceUrl;
	
	@Value("${username_banco}")
	private String dataSourceUser;
	
	@Value("${password_banco}")
	private String dataSourcePassword;


}
