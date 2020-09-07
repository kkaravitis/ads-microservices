package com.wordpress.kkaravitis.ad.search.application.port.outt;

import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdDisplayEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInSearchResultsEvent;

public interface SendStatisticsPort {

    void sendSearchResultsEvent(AdsInSearchResultsEvent event);

    void sendPagedSearchResultsEvent(AdsInPageResultsEvent event);

    void sendAdDisplayEvent(AdDisplayEvent event);
}
