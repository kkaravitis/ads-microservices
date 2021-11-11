package com.wordpress.kkaravitis.ad.sec.audit;

import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdDisplayEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInSearchResultsEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdAuditService {
    private final AdInPageResultsRepository adInPageResultsRepository;

    public void saveAdsInPageResultsEvent(AdsInPageResultsEvent event) {
        event.getResults().forEach(ad -> {
            if (adInPageResultsRepository.findBySearchIdAndAdIdAndPageNumberAndPerPage(event.getKey(), ad.getAdId(), ad.getPageNumber(), ad.getPerPage()) == null) {
                adInPageResultsRepository.insert(new AdInPageResultsEntity(event.getKey(), ad.getAdId(), ad.getPageNumber(), ad.getPerPage()));
            }
        });
    }

}
