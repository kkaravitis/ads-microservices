package com.wordpress.kkaravitis.ad.search.util.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class AdInSearchResults {
    private String adId;

    @Override
    public String toString() {
        return "AdInSearchResults{" +
                "adId='" + adId + '\'' +
                '}';
    }
}
