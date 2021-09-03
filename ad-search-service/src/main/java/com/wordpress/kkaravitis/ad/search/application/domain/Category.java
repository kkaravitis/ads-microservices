package com.wordpress.kkaravitis.ad.search.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="Category")
public class Category {
    @EqualsAndHashCode.Include
    @Id
    String identifier;

    String name;

    String description;
}
