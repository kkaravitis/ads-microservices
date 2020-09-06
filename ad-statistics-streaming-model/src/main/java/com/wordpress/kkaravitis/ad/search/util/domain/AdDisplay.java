package com.wordpress.kkaravitis.ad.search.util.domain;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdDisplay {
    private String adId;
    private boolean bySearch;
    private LocalDateTime displayDate;

    @Override
    public String toString() {
        return "AdDisplay{" +
                "AdId='" + adId + '\'' +
                ", bySearch=" + bySearch +
                ", displayDate=" + displayDate +
                '}';
    }
}
