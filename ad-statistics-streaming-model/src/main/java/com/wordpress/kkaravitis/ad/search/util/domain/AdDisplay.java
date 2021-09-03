package com.wordpress.kkaravitis.ad.search.util.domain;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class AdDisplay {
    private String adId;
    private boolean bySearch;
    private LocalDateTime displayDate;
}
