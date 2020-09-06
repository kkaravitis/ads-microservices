package com.wordpress.kkaravitis.ad.search.util.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class AdInPageResults {
    private Integer pageNumber;
    private Integer perPage;
    private String adId;

    @Override
    public String toString() {
        return "AdInPageResults{" +
                "pageNumber=" + pageNumber +
                ", perPage=" + perPage +
                ", adId='" + adId + '\'' +
                '}';
    }
}
