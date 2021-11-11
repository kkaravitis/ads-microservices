package com.wordpress.kkaravitis.ad.sec.audit;


import com.wordpress.kkaravitis.ad.search.util.kafka.Topics;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AdEventConsumer {
    private static final String AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID = "secondary-listener";

    private final AdAuditService auditService;

    @KafkaListener(id = AD_IN_PAGE_RESULTS_EVENT_LISTENER_ID,
            topics = Topics.PAGEABLE_SEARCH_STATISTICS_TOPIC,
            groupId = "${ad.page.kafka.consumer.group.id:empty}",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeAdsInPageResultsEvent(AdsInPageResultsEvent event) {
        auditService.saveAdsInPageResultsEvent(event);
    }
}
