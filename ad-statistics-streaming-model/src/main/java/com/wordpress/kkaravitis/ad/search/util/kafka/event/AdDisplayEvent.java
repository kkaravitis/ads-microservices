package com.wordpress.kkaravitis.ad.search.util.kafka.event;

import com.wordpress.kkaravitis.ad.search.util.domain.AdDisplay;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class AdDisplayEvent extends AdDisplay implements Serializable {

    @Builder
    public AdDisplayEvent(String adId, boolean bySearch, LocalDateTime displayDate) {
        super(adId, bySearch, displayDate);
    }
}
