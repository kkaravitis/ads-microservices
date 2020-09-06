package com.wordpress.kkaravitis.ad.search.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdDisplayEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInSearchResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.Topics;
import com.wordpress.kkaravitis.ad.search.AdDisplayEntity;
import com.wordpress.kkaravitis.ad.search.AdInPageResultsEntity;
import com.wordpress.kkaravitis.ad.search.AdInSearchResultsEntity;
import com.wordpress.kkaravitis.ad.search.AdDisplayRepository;
import com.wordpress.kkaravitis.ad.search.AdInPageResultsRepository;
import com.wordpress.kkaravitis.ad.search.AdInSearchResultsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AdEventConsumer {
    private static final String AD_DISPLAYS_EVENT_LISTENER_ID = "AD_DISPLAYS_EVENT_LISTENER_ID";
    private static final String AD_IN_SEARCH_RESULTS_EVENT_LISTENER_ID = "AD_IN_SEARCH_RESULTS_EVENT_LISTENER_ID";
    private static final String AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID = "AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID";

    private static final Logger logger = LoggerFactory.getLogger(AdEventConsumer.class);

    @Autowired
    private AdDisplayRepository adDisplayRepository;

    @Autowired
    private AdInSearchResultsRepository adInSearchResultsRepository;

    @Autowired
    private AdInPageResultsRepository adInPageResultsRepository;

    @KafkaListener(id = AD_DISPLAYS_EVENT_LISTENER_ID,
            topics = Topics.AD_DETAILS_DISPLAY_TOPIC,
            groupId = "${ad.details.kafka.consumer.group.id:empty}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeAdDisplaysEvent(String message) throws JsonProcessingException {
        AdDisplayEvent event = new ObjectMapper().readValue(message, AdDisplayEvent.class);
        AdDisplayEntity entity = adDisplayRepository.findByAdIdAndSearchAndDisplayDate(event.getAdId(),
                event.isBySearch(), event.getDisplayDate());
        if (entity == null) {
            adDisplayRepository.insert(new AdDisplayEntity(event.getAdId(), event.isBySearch(), event.getDisplayDate()));
        }
    }

    @KafkaListener(id = AD_IN_SEARCH_RESULTS_EVENT_LISTENER_ID,
            topics = Topics.SEARCH_STATISTICS_TOPIC,
            groupId = "${ad.search.kafka.consumer.group.id:empty}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeAdsInSearchEvent(String message) throws JsonProcessingException {
        AdsInSearchResultsEvent event = new ObjectMapper().readValue(message, AdsInSearchResultsEvent.class);
        event.getResults().forEach(ad -> {
            if (adInSearchResultsRepository.findBySearchIdAndAdId(event.getKey(), ad.getAdId()) == null) {
                adInSearchResultsRepository.insert(new AdInSearchResultsEntity(event.getKey(), ad.getAdId()));
            }
        });
    }

    @KafkaListener(id = AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID,
            topics = Topics.PAGEABLE_SEARCH_STATISTICS_TOPIC,
            groupId = "${ad.page.kafka.consumer.group.id:empty}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeAdsInPageResultsEvent(String message) throws JsonProcessingException {
        AdsInPageResultsEvent event = new ObjectMapper().readValue(message, AdsInPageResultsEvent.class);
        event.getResults().forEach(ad -> {
            if (adInPageResultsRepository.findBySearchIdAndAdIdAndPageNumberAndPerPage(event.getKey(), ad.getAdId(), ad.getPageNumber(), ad.getPerPage()) == null) {
                adInPageResultsRepository.insert(new AdInPageResultsEntity(event.getKey(), ad.getAdId(), ad.getPageNumber(), ad.getPerPage()));
            }
        });
    }
}
