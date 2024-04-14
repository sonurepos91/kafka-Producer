package com.streams.kafkaproject.producer;

import com.streams.kafkaproject.util.KafkaProducerConfigProps;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
Messages To Apache Kafka Can be send Using Either KafkaProduer API or KafkaTemplate
Both are Thread Safe
*/
@Component
public class MessageProducer implements MessageProducerInterFace {

    Logger log = LogManager.getLogger(MessageProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public String sendMessageUsingKafkaTemplate (String topic, String data) {
        {
            long key = System.currentTimeMillis() % new Random().nextInt();
            List<Header> headers = new ArrayList<>();
            String clientId = String.valueOf(kafkaTemplate.getProducerFactory().getConfigurationProperties().get(ProducerConfig.CLIENT_ID_CONFIG));
            headers.add(new RecordHeader(clientId, null));
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, System.currentTimeMillis(), String.valueOf(key), data, headers);
            try {
                CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
                future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Send Message : " + data);
                        log.info("Topic " + result.getProducerRecord().topic() + " key " + result.getProducerRecord().key() + " Value " + result.getProducerRecord().value() + " Partition " + result.getProducerRecord().partition() + " Producer Client Id : " + result.getProducerRecord().headers().toString());
                        log.info("Topic " + result.getRecordMetadata().topic() + " Offset " + result.getRecordMetadata().offset() + " Partition " + result.getRecordMetadata().partition() + " serializedKeySize " + result.getRecordMetadata().serializedKeySize() + " serializedValueSize " + result.getRecordMetadata().serializedValueSize());
                    } else {
                        log.info("Message " + data + " Not send due to " + ex.getMessage());
                    }
                });
            } catch (Exception ex) {
                log.info("exception due to : " + ex.getMessage());
                // kafkaTemplate.getProducerFactory().reset();
            }
            return "Message Sent Successfully";
        }
    }

    /*
    Make Sure We do not initiialize two Producer Instance with same Producer ID if so happens will throw
    javax.management.InstanceAlreadyExistsException
    */
    @Override
    public String sendMessageUsingKafkaProducder (String topic, String data) {
        long key = System.currentTimeMillis() % new Random().nextInt();
        Producer<String, String> producer = new KafkaProducer<>(KafkaProducerConfigProps.getConfig());
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, String.valueOf(key), data);
        try {
            RecordMetadata recordMetadata = producer.send(record).get(3000, TimeUnit.MILLISECONDS);
            log.info("... Topic.... " + recordMetadata.topic() + "  ....Partition... " + recordMetadata.partition() + " ....Offset.... " + recordMetadata.offset());
        } catch (ExecutionException ex) {
            log.info("exception due to : " + ex.getMessage());
        } catch (InterruptedException ex) {
            log.info("exception due to : " + ex.getMessage());
        } catch (TimeoutException ex) {
            log.info("exception due to : " + ex.getMessage());
        } catch (Exception ex) {
            log.info("exception due to : " + ex.getMessage());
        }
        return "Message Sent Successfully";
    }
}


