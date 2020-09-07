package com.wordpress.kkaravitis.ad.search;

import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdDisplayEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInSearchResultsEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdAuditService {
    private final AdDisplayRepository adDisplayRepository;
    private final AdInSearchResultsRepository adInSearchResultsRepository;
    private final AdInPageResultsRepository adInPageResultsRepository;

    public void saveAdDisplayEntity(AdDisplayEvent event) {
        AdDisplayEntity entity = adDisplayRepository.findByAdIdAndSearchAndDisplayDate(event.getAdId(),
                event.isBySearch(), event.getDisplayDate());
        if (entity == null) {
            adDisplayRepository.insert(new AdDisplayEntity(event.getAdId(), event.isBySearch(), event.getDisplayDate()));
        }
    }

    public void saveAdsInSearchEvent(AdsInSearchResultsEvent event) {
        event.getResults().forEach(ad -> {
            if (adInSearchResultsRepository.findBySearchIdAndAdId(event.getKey(), ad.getAdId()) == null) {
                adInSearchResultsRepository.insert(new AdInSearchResultsEntity(event.getKey(), ad.getAdId()));
            }
        });
    }

    public void saveAdsInPageResultsEvent(AdsInPageResultsEvent event) {
        event.getResults().forEach(ad -> {
            if (adInPageResultsRepository.findBySearchIdAndAdIdAndPageNumberAndPerPage(event.getKey(), ad.getAdId(), ad.getPageNumber(), ad.getPerPage()) == null) {
                adInPageResultsRepository.insert(new AdInPageResultsEntity(event.getKey(), ad.getAdId(), ad.getPageNumber(), ad.getPerPage()));
            }
        });
    }

}
