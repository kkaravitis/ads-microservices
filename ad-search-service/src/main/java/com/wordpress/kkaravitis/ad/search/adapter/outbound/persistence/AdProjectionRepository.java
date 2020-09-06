package com.wordpress.kkaravitis.ad.search.adapter.outbound.persistence;

import com.wordpress.kkaravitis.ad.search.application.AdDetailsProjection;
import com.wordpress.kkaravitis.ad.search.application.AdFilter;
import com.wordpress.kkaravitis.ad.search.application.AdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AdProjectionRepository {
    List<AdProjection> fetchAll(AdFilter filter, Sort sort);

    Page<AdProjection> fetchPaged(AdFilter filter, Pageable pageable);

    AdDetailsProjection fetchById(String adId);
}
