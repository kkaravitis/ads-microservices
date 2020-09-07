package com.wordpress.kkaravitis.ad.search.application;

import com.wordpress.kkaravitis.ad.search.application.port.in.AdServicePort;
import com.wordpress.kkaravitis.ad.search.application.port.outt.AdRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdService implements AdServicePort {

    private AdRepository repository;

    private AuditSearchService auditSearchService;

    @Override
    public SearchResponse getAds(AdServicePort.SearchCommand command) {
        auditSearchService.auditSearchResults(command.getFilter(), command.getPageable().getSort());
        Page<AdProjection> page =  repository.fetchPaged(command.getFilter(), command.getPageable());
        auditSearchService.auditPagedSearchResults(page.getContent(), command.getPageable());
        return SearchResponse.builder()
                .items(page.getContent())
                .totalResults(page.getTotalElements())
                .build();
    }

    @Override
    public AdDetailsProjection getAd(AdServicePort.GetDetailsCommand command) {
        AdDetailsProjection result = repository.fetchById(command.getIdentifier());
        auditSearchService.auditAd(result.getAdId(), command.isBySearch());
        return result;
    }
}
