package com.wordpress.kkaravitis.ad.search.application;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Customer {
    @EqualsAndHashCode.Include
    @Id
    Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CustomerRole role;
}
