package com.wordpress.kkaravitis.ad.search.application.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class AdProjection {
    private String id;
    private String shortDescription;
    private String customerName;
    private double price;

    @QueryProjection
    public AdProjection(String id, String shortDescription, double price, String customerName) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.price = price;
        this.customerName = customerName;
    }
}
