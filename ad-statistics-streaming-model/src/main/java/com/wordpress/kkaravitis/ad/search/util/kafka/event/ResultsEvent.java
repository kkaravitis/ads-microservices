package com.wordpress.kkaravitis.ad.search.util.kafka.event;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class ResultsEvent<T> implements Serializable {
    protected List<T> results;
    protected String key;
}
