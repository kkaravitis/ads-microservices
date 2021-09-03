package com.wordpress.kkaravitis.ad.search.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Entity(name="Ad")
@Table(name = "Ad")
public class Ad {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String shortDescription;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customerId")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId")
    private Category category;

    private Date createdAt;

    private Double price;
}