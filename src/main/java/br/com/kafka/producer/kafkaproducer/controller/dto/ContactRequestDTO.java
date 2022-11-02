package br.com.kafka.producer.kafkaproducer.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequestDTO {
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String phone;
	
	private String cpf;

}
