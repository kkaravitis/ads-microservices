package com.wordpress.kkaravitis.ad.search.application.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name="Customer")
public class Customer {
    @EqualsAndHashCode.Include
    @Id
    Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CustomerRole role;
}
