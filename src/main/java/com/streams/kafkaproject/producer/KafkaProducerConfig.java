package com.streams.kafkaproject.producer;

import com.streams.kafkaproject.util.KafkaProducerConfigProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class KafkaProducerConfig {


   @Bean
   public ProducerFactory<String,String> producerFactory(){
       return new DefaultKafkaProducerFactory<>(KafkaProducerConfigProps.getConfig());
   }

   @Bean
   public KafkaTemplate<String,String> kafkaTemplate(){
       return new KafkaTemplate<>(producerFactory());
   }

}
