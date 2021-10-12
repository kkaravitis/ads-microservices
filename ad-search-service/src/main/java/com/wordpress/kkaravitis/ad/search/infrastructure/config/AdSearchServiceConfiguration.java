package com.wordpress.kkaravitis.ad.search.infrastructure.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableSwagger2
public class AdSearchServiceConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapAddress;

//    @Value("${kafka.hostname:empty}")
//    private String kafkaHostName;
//
//    @Value("${kafka.port:0}")
//    private int kafkaPort;

    @Value("${kafka.maxPartitionFetchBytes:1024}")
    private String maxPartitionFetchBytes;

    @Value("${kafka.fetchMaxBytes:1024}")
    private String fetchMaxBytes;

    @Value("${kafka.maxRequestSize:1024}")
    private String maxRequestSize;

    @Value("${kafka.bufferMemorySize:1024}")
    private String bufferMemorySize;


    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 300000);


        return props;
    }

    private ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Returns the spring kafka template
     * @return the spring kafka template
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public Docket configure() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
