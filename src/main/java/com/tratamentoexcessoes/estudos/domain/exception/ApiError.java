package com.tratamentoexcessoes.estudos.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;


/* 
 * Classe para atender a RFC 7807 - Problem Details - Esta define o modelo de retorno contendo as informações sobre
 * a exception gerada. os campos abaixo status, type, title e opcionalmente userMessage, definem as informações que 
 * devem ser retornada em caso de falha por qualquer mótivo. Desses somente o campo userMessage é considerado como opcional.
 */

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ApiError {
	/*
	 * Os quatro campos abaixo são obrigatórios para atender a RFC
	 */
	private Integer status;
	private String type;
	private String title;
	private String detail;
	/*
	 * o campo abaixo é considerado como um campo opcional, sendo necessário somente para retornar informações para o usuário final.
	 */
	private String userMessage;


}
