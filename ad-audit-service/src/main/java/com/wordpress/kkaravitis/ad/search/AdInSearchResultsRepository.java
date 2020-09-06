package com.wordpress.kkaravitis.ad.search;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdInSearchResultsRepository extends MongoRepository<AdInSearchResultsEntity, String> {
    AdInSearchResultsEntity findBySearchIdAndAdId(String searchId, String adId);
}
