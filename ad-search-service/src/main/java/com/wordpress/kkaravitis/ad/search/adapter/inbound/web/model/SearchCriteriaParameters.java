package com.wordpress.kkaravitis.ad.search.adapter.inbound.web.model;

import com.wordpress.kkaravitis.ad.search.application.CustomerRole;
import lombok.Data;

@Data
public class SearchCriteriaParameters {
    private String term;
    private String categoryIdentifier;
    private CustomerRole customerRole;
    private String customerName;
}
