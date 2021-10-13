package com.wordpress.kkaravitis.ad.search;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class
 */
@Configuration
public class AdAuditServiceConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String kafkaBootstrapAddress;

//  @Value("${kafka.hostname:empty}")
//  private String kafkaHostName;
//
//  @Value("${kafka.port:0}")
//  private int kafkaPort;

  @Value("${kafka.maxPartitionFetchBytes:1024}")
  private String maxPartitionFetchBytes;

  @Value("${kafka.fetchMaxBytes:1024}")
  private String fetchMaxBytes;

  @Value("${kafka.maxRequestSize:1024}")
  private String maxRequestSize;

  @Value("${kafka.bufferMemorySize:1024}")
  private String bufferMemorySize;

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
    objectMapper.registerModule(new JavaTimeModule());
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
    return factory;
  }
}
