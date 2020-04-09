package com.tratamentoexcessoes.estudos.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.tratamentoexcessoes.estudos.domain.exception.ApiError;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	/*
	 *Esta classe foi criada para indicar que a mesma é um Exception Handler Universal, ou seja qualquer método dos controladores que recebe uma 
	 *exception conhecida por ela será capturado por essa classe.
	 *Mas para isso a classe deve receber a anotação @ControllerAdvice e extender de ResponseEntityExceptionHandler.
	 */
	
	
	/*
	 * Metódo anotado com @ExceptionHandler, este metódo irá capturar toda excessão desse tipo
	 * para trata-la irá receber os parâmetros:
	 * - Tipo da Excessão, que é o mesmo tipo passado para a anotação 
	 * - WebRequest, interface que estende de RequestAttributes
	 * 
	 * Após interceptar a exception o método prepara as informações de acordo com os parametros
	 * recebidos e chama o método sub-escrito da classe ResponseEntityExceptionHandler passando
	 * os parâmetros personalizados.
	 */
	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<?> handleRegistroNaoEncontradoException(
			RegistroNaoEncontradoException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ApiErrorType apiErrorType = ApiErrorType.REGISTRO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		ApiError apiError = CreateErrorApiBuilder(status, apiErrorType, detail).build();
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);

	}
	
	/*
	 * Metódo anotado com @ExceptionHandler, este metódo irá capturar toda excessão desse tipo
	 * para trata-la irá receber os parâmetros:
	 * - Tipo da Excessão, que é o mesmo tipo passado para a anotação 
	 * - WebRequest, interface que estende de RequestAttributes
	 * 
	 * Após interceptar a exception o método prepara as informações de acordo com os parametros
	 * recebidos e chama o método sub-escrito da classe ResponseEntityExceptionHandler passando
	 * os parâmetros personalizados.
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			  WebRequest request){
		
		ApiErrorType apiErrorType = ApiErrorType.PARAMETRO_URL_INCORRETO;
		
		HttpHeaders header = HttpHeaders.EMPTY;
		
		String detail = "A url contém um parâmetro inválido, verifique o valor passado. "; 
		
		ApiError apiError = CreateErrorApiBuilder(HttpStatus.BAD_REQUEST , apiErrorType, detail).build();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		return handleExceptionInternal(ex, apiError,header,status, request);
	}
	
	/*
	 * Método que recebe uma excessão do tipo HttpMessageNotReadableException, mas essa exception
	 * é uma super classe, que possui várias subclasses. Por esse motivo esse método recebe a super classe
	 * e com o método getRootCause do pacote org.apache.commons.lang3.exception.ExceptionUtils (pacote esse
	 * que o jar deve ser baixado no poom), especializa a subclasse sendo possível especificar ainda mais 
	 * a exception. Com a informação da subclasse o método verifica, caso a subException seja implementada
	 * o método chama outro método especialista, senão trata a excessão como a super classe.
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable causaRaiz = org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(ex);
		
		if (causaRaiz instanceof JsonParseException) {
			return handleJsonParseException((JsonParseException)causaRaiz,headers,status,request);
		}else 
		if (causaRaiz instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedPropertyException((UnrecognizedPropertyException)causaRaiz,headers,status,request);
		}
		
		ApiErrorType apiErrorType = ApiErrorType.CORPO_FORA_PADRAO;
		String detail ="O corpo da requisição esta inválido, verifique seu conteúdo"; 
		ApiError apiError = CreateErrorApiBuilder(status, apiErrorType, detail).build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}

	/*
	 * Método sub-escrito da classe ResponseEntityExceptionHandler, precisamos subscreve-lo
	 * porque o método da classe pai, recebe informações do trace da aplicação o que não é uma boa prática
	 * além disso as informações são muito técnicas e em Inglês, tornando-as ilegíveis.
	 * Dessa maneira podemos tratar o body do response e envia-lo para a classe super com o corpo pesonalizado. 
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ApiErrorType apiErrorType = ApiErrorType.RECURSO_NAO_ENCONTRATO;
		String detail ="O recurso ou método não foi encontrado na API, por favor verifique a url."; 
		ApiError apiError = CreateErrorApiBuilder(status, apiErrorType, detail)
							.userMessage("Houve uma falha na aplicação cliente, verifique com o adm do sistema.")
							.build();
		HttpHeaders header = HttpHeaders.EMPTY;
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		return super.handleExceptionInternal(ex, apiError, header, httpStatus, request);
	}
	
	/*
	 * Método interno usado para especializar a exception UnrecognizedPropertyException
	 * que é uma sub-exception de handleHttpMessageNotReadable.
	 */
	private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){
		ApiErrorType apiErrorType = ApiErrorType.CAMPOS_DESCONHECIDOS;
		
		String detail = "O corpo da requisição contém valores desconhecidos para a essa versão, " 
						+ "verifique os campos que devem ser enviados. "; 
		
		ApiError apiError = CreateErrorApiBuilder(status, apiErrorType, detail).build();
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);	
	}

	/*
	 * Método interno usado para especializar a exception JsonParseException
	 * que é uma sub-exception de handleHttpMessageNotReadable.
	 */
	private ResponseEntity<Object> handleJsonParseException(JsonParseException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){
		
		ApiErrorType apiErrorType = ApiErrorType.CAMPOS_FORA_PADRAO;
		
		String detail = "O corpo da requisição contém valores inválidos, não foi possível converte-los" 
						+ " para os tipos definidos. "; 
		
		ApiError apiError = CreateErrorApiBuilder(status, apiErrorType, detail).build();
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	/*
	 * Método sub-escrito da classe ResponseEntityExceptionHandler, precisamos subscreve-lo
	 * porque o método da classe pai, recebe informações do trace da aplicação o que não é uma boa prática
	 * além disso as informações são muito técnicas e em Inglês, tornando-as ilegíveis.
	 * Dessa maneira podemos tratar o body do response e envia-lo para a classe super com o corpo pesonalizado. 
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			 body = ApiError.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();

		}else if (body instanceof String) {
			 body = ApiError.builder()
						.title((String) body)
						.status(status.value())
						.build();
		}
	
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	/*
	 * Método usado para facilitar o uso do modelo do body este apenas retorna uma instancia de um Builder
	 * isso se faz necessário para que seja possível incluir alguma nova informação depois do retorno desse método
	 * pois o mesmo não retorna um objeto pronto, e sim um  ApiErrorBuilder.
	 */
	private ApiError.ApiErrorBuilder CreateErrorApiBuilder(HttpStatus status, ApiErrorType errorType, String detail) {		
		return ApiError.builder()
				.status(status.value())
				.type(errorType.getPath())
				.title(errorType.getTitle())
				.detail(detail);
	}
}
