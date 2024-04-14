package com.streams.kafkaproject.producer;

public interface MessageProducerInterFace {

     String sendMessageUsingKafkaTemplate(String topic, String data);

     String sendMessageUsingKafkaProducder(String topic, String data);
}
