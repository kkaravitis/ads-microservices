package com.wordpress.kkaravitis.ad.search.util.kafka.event;

import com.wordpress.kkaravitis.ad.search.util.domain.AdInPageResults;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AdsInPageResultsEvent extends ResultsEvent<AdInPageResults> {
    @Builder
    public AdsInPageResultsEvent(List<AdInPageResults> results, String key) {
        super(results, key);
    }
}
