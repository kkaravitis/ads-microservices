package com.wordpress.kkaravitis.ad.search.adapter.outbound.persistence;

import com.wordpress.kkaravitis.ad.search.application.Ad;
import com.wordpress.kkaravitis.ad.search.application.port.out.AdRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdJpaRepository extends JpaRepository<Ad, String>, AdProjectionRepository, AdRepository {
}
