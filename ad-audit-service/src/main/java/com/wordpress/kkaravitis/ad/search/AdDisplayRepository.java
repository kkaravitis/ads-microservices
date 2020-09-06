package com.wordpress.kkaravitis.ad.search;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdDisplayRepository extends MongoRepository<AdDisplayEntity, String> {
    AdDisplayEntity findByAdIdAndSearchAndDisplayDate(String adId, boolean search, LocalDateTime displayDate);
}
