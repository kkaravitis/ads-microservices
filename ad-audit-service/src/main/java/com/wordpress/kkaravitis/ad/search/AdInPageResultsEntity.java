package com.wordpress.kkaravitis.ad.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AdPageResults")
public class AdInPageResultsEntity {
    @Id
    private String id;
    private String searchId;
    private Integer pageNumber;
    private Integer perPage;
    private String adId;

    public AdInPageResultsEntity(String searchId, String adId, Integer pageNumber, Integer perPage) {
        this.searchId = searchId;
        this.pageNumber = pageNumber;
        this.perPage = perPage;
        this.adId = adId;
    }

    public String getSearchId() {
        return searchId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public String getAdId() {
        return adId;
    }

    @Override
    public String toString() {
        return "AdInPageResultsEntity{" +
                "searchId='" + searchId + '\'' +
                ", pageNumber=" + pageNumber +
                ", perPage=" + perPage +
                ", adId='" + adId + '\'' +
                '}';
    }
}
