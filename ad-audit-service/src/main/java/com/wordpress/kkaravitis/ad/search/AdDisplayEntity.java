package com.wordpress.kkaravitis.ad.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "adDisplays")
public class AdDisplayEntity {
    @Id
    private String id;

    private String adId;
    private boolean search;
    private LocalDateTime displayDate;

    public AdDisplayEntity(String adId, boolean search, LocalDateTime displayDate) {
        this.adId = adId;
        this.search = search;
        this.displayDate = displayDate;
    }

    public String getAdId() {
        return adId;
    }

    public boolean isSearch() {
        return search;
    }

    public LocalDateTime getDisplayDate() {
        return displayDate;
    }

    @Override
    public String toString() {
        return "AdDisplayEntity{" +
                ", adId='" + adId + '\'' +
                ", search=" + search +
                ", displayDate=" + displayDate +
                '}';
    }
}
