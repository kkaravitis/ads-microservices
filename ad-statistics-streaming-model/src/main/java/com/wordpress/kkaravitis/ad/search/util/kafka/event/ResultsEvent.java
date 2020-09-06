package com.wordpress.kkaravitis.ad.search.util.kafka.event;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class ResultsEvent<T> implements Serializable {
    protected List<T> results;
    protected String key;

    @Override
    public String toString() {
        return "ResultsEvent{" +
                "results=" + results +
                ", key='" + key + '\'' +
                '}';
    }
}
