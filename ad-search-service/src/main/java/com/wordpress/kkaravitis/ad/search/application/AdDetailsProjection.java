package com.wordpress.kkaravitis.ad.search.application;

import com.querydsl.core.annotations.QueryProjection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@EqualsAndHashCode
@ToString
public class AdDetailsProjection {
    private String adId;
    private String description;
    private String customerName;
    private CustomerRole customerRole;
    private Double price;
    private Date createdAt;

    @QueryProjection
    public AdDetailsProjection(String adId, String description, String customerName, CustomerRole customerRole, Double price, Date createdAt) {
        this.adId = adId;
        this.description = description;
        this.customerName = customerName;
        this.customerRole = customerRole;
        this.price = price;
        this.createdAt = createdAt;
    }
}
