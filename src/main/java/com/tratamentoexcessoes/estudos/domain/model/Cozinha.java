package com.tratamentoexcessoes.estudos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/*
 * Classe modelo e entidade usando spring data jpa
 */

@Data
@Table
@Entity(name = "Cozinha")
public class Cozinha {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long id;
	
	@Column(name = "descricao", length = 30)   
	private String nome;
	
	@Column(name = "numero")
	private Long numero;

}
