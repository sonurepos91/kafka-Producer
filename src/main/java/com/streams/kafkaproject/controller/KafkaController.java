package com.streams.kafkaproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streams.kafkaproject.model.StudentDTO;
import com.streams.kafkaproject.producer.MessageProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    Logger log = LogManager.getLogger(KafkaController.class);

    @Autowired
    private MessageProducer messageProducer;


    @PostMapping("/sendToKafkaTemplate")
    public ResponseEntity<String> sendMessageUsingKafkaTemplate(@RequestBody StudentDTO studentDTO, @RequestParam String topic) {
        return new ResponseEntity<>(messageProducer.sendMessageUsingKafkaTemplate(topic,getProducerRecord(studentDTO)),HttpStatus.OK);
    }

    @PostMapping("sendToKafkaProducer")
    public ResponseEntity<String> sendMessageUsingKafkaProducer(@RequestBody StudentDTO studentDTO ,@RequestParam String topic){
        return new ResponseEntity<>(messageProducer.sendMessageUsingKafkaProducder(topic,getProducerRecord(studentDTO)),HttpStatus.OK);
    }

    private String getProducerRecord(StudentDTO studentDTO) {
        String data ="";
        ObjectMapper mapper = new ObjectMapper();
        try {
            data = mapper.writeValueAsString(studentDTO);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return data;
    }
}
