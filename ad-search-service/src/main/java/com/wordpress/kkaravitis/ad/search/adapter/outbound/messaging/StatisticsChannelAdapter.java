package com.wordpress.kkaravitis.ad.search.adapter.outbound.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordpress.kkaravitis.ad.search.application.port.outbound.SendStatisticsPort;
import com.wordpress.kkaravitis.ad.search.util.domain.AdInSearchResults;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdDisplayEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInSearchResultsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class StatisticsChannelAdapter implements SendStatisticsPort {
    private Integer chunkSize = 100;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String searchStatisticsTopic;

    private final String  pageableSearchStatisticsTopic;

    private final String adDetailsDisplaysTopic;

    private Integer concurrentWrites = 12;

    public StatisticsChannelAdapter(KafkaTemplate<String, String> kafkaTemplate,
                                    @Value("${kafka.search.statistics.topic:searchStatisticsTopic}")
                                            String searchStatisticsTopic,
                                    @Value("${kafka.paged.search.statistics.topic:testTopic}")
                                    String pageableSearchStatisticsTopic,
                                    @Value("${kafka.ad.details.displays.statistics.topic:adDetailsDisplaysTopic}")
                                    String adDetailsDisplaysTopic,
                                    @Value("${kafka.ad.message.chunk:100}")
                                    Integer chunkSize) {
        this.kafkaTemplate = kafkaTemplate;
        this.searchStatisticsTopic = searchStatisticsTopic;
        this.pageableSearchStatisticsTopic = pageableSearchStatisticsTopic;
        this.adDetailsDisplaysTopic = adDetailsDisplaysTopic;
        this.chunkSize = chunkSize;
    }

    @Override
    public void sendSearchResultsEvent(AdsInSearchResultsEvent event) {
        int listSize = event.getResults().size();
        int perList = chunkSize;
        for (int i = 0; i < listSize; i += perList) {
            int start = i;
            int end = Math.min(listSize, i + perList);
            List<AdInSearchResults> sublist = new ArrayList<>(event.getResults().subList(start, end));
            try {
                String message = new ObjectMapper().writeValueAsString(event);
                kafkaTemplate.send(searchStatisticsTopic, message);
            } catch (JsonProcessingException e) {
                log.error("unable to json process the results event \n" +  event.toString());
            }
        }
    }

    @Override
    public void sendPagedSearchResultsEvent(AdsInPageResultsEvent event) {
        try {
            String message = new ObjectMapper().writeValueAsString(event);
            kafkaTemplate.send(pageableSearchStatisticsTopic, message);
        } catch (JsonProcessingException e) {
            log.error("unable to json process the results event \n" +  event.toString());
        }
    }

    @Override
    public void sendMultiThread(AdsInPageResultsEvent event) {
        ExecutorService executorService = Executors.newFixedThreadPool(concurrentWrites);
        CountDownLatch latch = new CountDownLatch(concurrentWrites);
        for (int i=0; i<concurrentWrites; i++) {
            Runnable runnable = () -> {
                this.sendPagedSearchResultsEvent(event);
                latch.countDown();
            };
            executorService.execute(runnable);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        executorService.shutdown();
    }

    @Override
    public void sendAdDisplayEvent(AdDisplayEvent event) {
        try {
            String message = new ObjectMapper().writeValueAsString(event);
            kafkaTemplate.send(adDetailsDisplaysTopic, message);
        } catch (JsonProcessingException e) {
            log.error("unable to json process the ad display event \n" + event.toString());
        }
    }
}
