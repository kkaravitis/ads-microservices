package com.wordpress.kkaravitis.ad.search.util.domain;

import lombok.*;

@Setter
@Getter
@ToString
public class AdInSearchResults {
    private String adId;

    @Override
    public String toString() {
        return "AdInSearchResults{" +
                "adId='" + adId + '\'' +
                '}';
    }
}
