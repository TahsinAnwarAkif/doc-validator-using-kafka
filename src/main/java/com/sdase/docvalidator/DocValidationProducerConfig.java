package com.sdase.docvalidator;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akif
 * @since 3/10/22
 */
@Configuration
public class DocValidationProducerConfig {

	@Value("${spring.kafka.bootstrap.servers}")
	private String bootstrapServers;

	@Bean
	@SuppressWarnings("unchecked")
	public ProducerFactory producerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory(config);
	}


	@Bean
	@SuppressWarnings("unchecked")
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<String, Object>(producerFactory());
	}
}
