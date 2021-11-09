package com.wordpress.kkaravitis.ad.search.application.domain;

import com.wordpress.kkaravitis.ad.search.application.port.outbound.AdRepository;
import com.wordpress.kkaravitis.ad.search.application.port.outbound.SendStatisticsPort;
import com.wordpress.kkaravitis.ad.search.util.domain.AdInPageResults;
import com.wordpress.kkaravitis.ad.search.util.domain.AdInSearchResults;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdDisplayEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInSearchResultsEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AuditSearchService {
    private static Integer CHUNK_SIZE = 100;

   private SendStatisticsPort statisticsChannelPort;

   private AdRepository repository;

    @Async
    public void auditSearchResults(AdFilter filter, Sort sort) {
        List<AdProjection> searchResults = repository.fetchAll(filter, sort);
        List<AdInSearchResults> statistics = searchResults.stream().map(ad -> {
            AdInSearchResults adInSearchResults = new AdInSearchResults();
            adInSearchResults.setAdId(ad.getId());
            return adInSearchResults;
        }).collect(Collectors.toList());
        AdsInSearchResultsEvent event = new AdsInSearchResultsEvent();
        event.setResults(statistics);
        event.setKey(UUID.randomUUID().toString());

        //statisticsChannelPort.sendSearchResultsEvent(event);
    }

    @Async
    public void auditPagedSearchResults(List<AdProjection> results, Pageable pageable) {
        AdsInPageResultsEvent event = new AdsInPageResultsEvent();
        event.setResults(results.stream().map(r -> {
            AdInPageResults adInPageResults = new AdInPageResults();
            adInPageResults.setAdId(r.getId());
            adInPageResults.setPageNumber(pageable.getPageNumber());
            adInPageResults.setPerPage(pageable.getPageSize());
            return adInPageResults;
        }).collect(Collectors.toList()));

        event.setKey(UUID.randomUUID().toString());

        //statisticsChannelPort.sendPagedSearchResultsEvent(event);
        statisticsChannelPort.sendMultiThread(event);
    }

    @Async
    public void auditAd(String adId, boolean bySearch) {
        AdDisplayEvent event = new AdDisplayEvent();
        event.setBySearch(bySearch);
        event.setAdId(adId);
        event.setDisplayDate(LocalDateTime.now());
        //statisticsChannelPort.sendAdDisplayEvent(event);
    }
}
