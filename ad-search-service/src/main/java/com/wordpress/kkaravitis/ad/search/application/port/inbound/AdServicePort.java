package com.wordpress.kkaravitis.ad.search.application.port.inbound;

import com.wordpress.kkaravitis.ad.search.application.AdDetailsProjection;
import com.wordpress.kkaravitis.ad.search.application.AdFilter;
import com.wordpress.kkaravitis.ad.search.application.SearchResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

public interface AdServicePort {

    SearchResponse getAds(SearchCommand command);

    AdDetailsProjection getAd(GetDetailsCommand command);

    @Builder
    @Getter
    class GetDetailsCommand {
        private final String identifier;
        private final boolean bySearch;
    }

    @Builder
    @Getter
    class SearchCommand {
        private final Pageable pageable;
        private final AdFilter filter;
    }
}
