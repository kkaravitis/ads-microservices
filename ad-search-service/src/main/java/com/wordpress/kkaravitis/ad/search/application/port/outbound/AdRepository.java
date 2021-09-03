package com.wordpress.kkaravitis.ad.search.application.port.outbound;

import com.wordpress.kkaravitis.ad.search.application.domain.AdDetailsProjection;
import com.wordpress.kkaravitis.ad.search.application.domain.AdFilter;
import com.wordpress.kkaravitis.ad.search.application.domain.AdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AdRepository {
    List<AdProjection> fetchAll(AdFilter filter, Sort sort);

    Page<AdProjection> fetchPaged(AdFilter filter, Pageable pageable);

    AdDetailsProjection fetchById(String adId);
}
