package br.com.kafka.producer.kafkaproducer.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kafka.producer.kafkaproducer.controller.dto.ContactRequestDTO;
import br.com.kafka.producer.kafkaproducer.controller.dto.MessageBodyDTO;
import br.com.kafka.producer.kafkaproducer.produtor.TopicProducer;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/kafka")
public class ProducerController {
	
	@Autowired
	private TopicProducer topicProducer;

	@PostMapping(value = "/send")
	public void send(@RequestBody MessageBodyDTO messageBody){
        topicProducer.send(messageBody.getMessage());
    }
	
	@PostMapping(value = "/sendBody")
    public void send(@RequestBody ContactRequestDTO messageBody){
        topicProducer.sendWithBody(messageBody);
    }
	
	@GetMapping()
	public Object listContacts() throws InterruptedException, ExecutionException {
		return topicProducer.sendList();
	}

}
