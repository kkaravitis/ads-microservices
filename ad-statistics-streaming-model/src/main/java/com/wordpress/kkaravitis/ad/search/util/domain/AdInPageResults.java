package com.wordpress.kkaravitis.ad.search.util.domain;

import lombok.*;

@Setter
@Getter
@ToString
public class AdInPageResults {
    private Integer pageNumber;
    private Integer perPage;
    private String adId;
}
