package br.com.kafka.producer.kafkaproducer.controller.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactResponseDTO {
	
	List<ContactRequestDTO> contacts;

}
