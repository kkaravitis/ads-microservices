package com.wordpress.kkaravitis.ad.search.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;


@Getter
@Builder
public class SearchResponse implements Serializable {
    private List<AdProjection> items;

    private Long totalResults;
}