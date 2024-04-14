package com.streams.kafkaproject.util;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerConfigProps {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9093,127.0.0.1:9094,127.0.0.1:9095";
    private static final String KEY_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer";
    private static final String VALUE_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer";
    private static final String CLIENT_ID = "KafkaProducer";
    private static final String ACKS_CONFIG = "all";

    public static Map<String, Object> getConfig () {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KEY_SERIALIZER_CLASS);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        props.put(ProducerConfig.ACKS_CONFIG, ACKS_CONFIG);

        // If we want messages to be distribute by kafka among the Brokers in the cluster in RoundRobbin Way
        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);
        return props;
    }
}
