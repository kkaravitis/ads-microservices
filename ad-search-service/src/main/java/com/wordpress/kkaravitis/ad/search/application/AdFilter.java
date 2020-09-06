package com.wordpress.kkaravitis.ad.search.application;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdFilter {
    private final String term;
    private final String categoryIdentifier;
    private final CustomerRole customerRole;
    private final String customerName;
}
