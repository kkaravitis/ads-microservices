package com.wordpress.kkaravitis.ad.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdDisplayEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInSearchResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.Topics;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AdEventConsumer {
    private static final String AD_DISPLAYS_EVENT_LISTENER_ID = "AD_DISPLAYS_EVENT_LISTENER_ID";
    private static final String AD_IN_SEARCH_RESULTS_EVENT_LISTENER_ID = "AD_IN_SEARCH_RESULTS_EVENT_LISTENER_ID";
    private static final String AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID = "AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID";

    private static final Logger logger = LoggerFactory.getLogger(AdEventConsumer.class);

    private final AdAuditService auditService;

    @KafkaListener(id = AD_DISPLAYS_EVENT_LISTENER_ID,
            topics = Topics.AD_DETAILS_DISPLAY_TOPIC,
            groupId = "${ad.details.kafka.consumer.group.id:empty}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeAdDisplaysEvent(String message) throws JsonProcessingException {
        AdDisplayEvent event = new ObjectMapper().readValue(message, AdDisplayEvent.class);
        auditService.saveAdDisplayEntity(event);
    }

    @KafkaListener(id = AD_IN_SEARCH_RESULTS_EVENT_LISTENER_ID,
            topics = Topics.SEARCH_STATISTICS_TOPIC,
            groupId = "${ad.search.kafka.consumer.group.id:empty}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeAdsInSearchEvent(String message) throws JsonProcessingException {
        AdsInSearchResultsEvent event = new ObjectMapper().readValue(message, AdsInSearchResultsEvent.class);
        auditService.saveAdsInSearchEvent(event);
    }

    @KafkaListener(id = AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID,
            topics = Topics.PAGEABLE_SEARCH_STATISTICS_TOPIC,
            groupId = "${ad.page.kafka.consumer.group.id:empty}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeAdsInPageResultsEvent(String message) throws JsonProcessingException {
        AdsInPageResultsEvent event = new ObjectMapper().readValue(message, AdsInPageResultsEvent.class);
        auditService.saveAdsInPageResultsEvent(event);
    }
}
