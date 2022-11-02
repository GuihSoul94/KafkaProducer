package br.com.kafka.producer.kafkaproducer.produtor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.kafka.producer.kafkaproducer.controller.dto.ContactRequestDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TopicProducer {
	
	@Value("${topic.name.producer: topico.comando.teste}")
	private String topicName;
	
	public CompletableFuture<Object> listaTest = new CompletableFuture<Object>();
	
	private final KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(producerFactory());
	
	
	public void send(String message) {
		kafkaTemplate.send(topicName, message);
	}
	
	public void sendWithBody(ContactRequestDTO contact) {
		String topic = "topico.contato.teste";
		Gson gson = new Gson();
		kafkaTemplate.send(topic, gson.toJson(contact));
	}
	
	public Object sendList() throws InterruptedException, ExecutionException {
		String topic = "topico.lista.consumidor";
		kafkaTemplate.send(topic, "");
		Object response = listaTest.get();
		return response;
	}
	
    @KafkaListener(topics = "topico.lista.producer", groupId = "group_id")
    public void consumeListContacts(ConsumerRecord<String, String> payload){
    	log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());
        var response = new Gson().fromJson(payload.value(), Object.class);
        	 listaTest.complete(response);
      }
	
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          "127.0.0.1:9092");
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
  
}
