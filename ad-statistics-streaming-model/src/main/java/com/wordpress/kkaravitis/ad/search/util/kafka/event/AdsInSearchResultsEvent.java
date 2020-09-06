package com.wordpress.kkaravitis.ad.search.util.kafka.event;

import com.wordpress.kkaravitis.ad.search.util.domain.AdInSearchResults;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AdsInSearchResultsEvent extends ResultsEvent<AdInSearchResults> {
    @Builder
    public AdsInSearchResultsEvent(List<AdInSearchResults> results, String key) {
        super(results, key);
    }
}
