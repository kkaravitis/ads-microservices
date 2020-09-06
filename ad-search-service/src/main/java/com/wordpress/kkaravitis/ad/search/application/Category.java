package com.wordpress.kkaravitis.ad.search.application;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Category {
    @EqualsAndHashCode.Include
    @Id
    String identifier;

    String name;

    String description;
}
