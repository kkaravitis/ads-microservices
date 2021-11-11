package com.wordpress.kkaravitis.ad.sec.audit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class
 */
@Configuration
public class AdAuditServiceConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String kafkaBootstrapAddress;

  @Value("${kafka.maxPartitionFetchBytes:1024}")
  private String maxPartitionFetchBytes;

  @Value("${kafka.fetchMaxBytes:1024}")
  private String fetchMaxBytes;

  @Value("${kafka.maxRequestSize:1024}")
  private String maxRequestSize;

  @Value("${kafka.bufferMemorySize:1024}")
  private String bufferMemorySize;

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, AdsInPageResultsEvent> concurrentKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, AdsInPageResultsEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(testTopicConsumerFactory());

    return factory;
  }

  @Bean
  public ConsumerFactory<String, AdsInPageResultsEvent> testTopicConsumerFactory() {

    return new DefaultKafkaConsumerFactory<>(consumerProperties(), new StringDeserializer(),
            new JsonDeserializer<>(AdsInPageResultsEvent.class, false));
  }

  private Map<String, Object> consumerProperties() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return props;
  }


}
