package com.wordpress.kkaravitis.ad.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "adSearchResults")
public class AdInSearchResultsEntity {
    @Id
    private String id;

    private String searchId;

    private String adId;

    public String getAdId() {
        return adId;
    }

    public String getSearchId() {
        return searchId;
    }

    public AdInSearchResultsEntity(String searchId, String adId) {
        this.searchId = searchId;
        this.adId = adId;
    }

    @Override
    public String toString() {
        return "AdInSearchResultsEntity{" +
                "searchId='" + searchId + '\'' +
                ", adId='" + adId + '\'' +
                '}';
    }
}
