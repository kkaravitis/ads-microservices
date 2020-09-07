package com.wordpress.kkaravitis.ad.search.application;

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
        List<AdInSearchResults> statistics = searchResults.stream().map(ad -> AdInSearchResults.builder()
                    .adId(ad.getId())
                    .build()
        ).collect(Collectors.toList());
        statisticsChannelPort.sendSearchResultsEvent(AdsInSearchResultsEvent.builder()
                .key(UUID.randomUUID().toString())
                .results(statistics)
                .build());
    }

    @Async
    public void auditPagedSearchResults(List<AdProjection> results, Pageable pageable) {
        statisticsChannelPort.sendPagedSearchResultsEvent(AdsInPageResultsEvent.builder()
                .key(UUID.randomUUID().toString())
                .results(results.stream().map(r -> AdInPageResults.builder()
                        .adId(r.getId())
                        .pageNumber(pageable.getPageNumber())
                        .perPage(pageable.getPageSize())
                        .build())
                        .collect(Collectors.toList()))
                .build());
    }

    @Async
    public void auditAd(String adId, boolean bySearch) {
        statisticsChannelPort.sendAdDisplayEvent(AdDisplayEvent.builder()
                .adId(adId)
                .bySearch(bySearch)
                .displayDate(LocalDateTime.now())
                .build());
    }
}
