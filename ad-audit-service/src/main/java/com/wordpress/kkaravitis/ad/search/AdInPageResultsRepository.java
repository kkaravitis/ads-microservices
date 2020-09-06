package com.wordpress.kkaravitis.ad.search;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdInPageResultsRepository extends MongoRepository<AdInPageResultsEntity, String> {
    AdInPageResultsEntity findBySearchIdAndAdIdAndPageNumberAndPerPage(String searchId, String adId, Integer pageNumber, Integer perPage);
}
