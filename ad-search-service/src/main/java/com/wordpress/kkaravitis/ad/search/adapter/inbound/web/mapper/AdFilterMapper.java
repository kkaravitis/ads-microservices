package com.wordpress.kkaravitis.ad.search.adapter.inbound.web.mapper;

import com.wordpress.kkaravitis.ad.search.application.AdFilter;
import com.wordpress.kkaravitis.ad.search.adapter.inbound.web.model.SearchCriteriaParameters;
import org.springframework.stereotype.Component;

@Component
public class AdFilterMapper {
    public AdFilter map(SearchCriteriaParameters parameters) {
        return AdFilter.builder()
                .categoryIdentifier(parameters.getCategoryIdentifier())
                .customerName(parameters.getCustomerName())
                .term(parameters.getTerm())
                .customerRole(parameters.getCustomerRole())
                .build();
    }
}
