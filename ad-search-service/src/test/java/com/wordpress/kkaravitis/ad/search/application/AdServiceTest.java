package com.wordpress.kkaravitis.ad.search.application;

import com.wordpress.kkaravitis.ad.search.application.domain.*;
import com.wordpress.kkaravitis.ad.search.application.port.inbound.AdServicePort;
import com.wordpress.kkaravitis.ad.search.application.port.outbound.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class AdServiceTest {

    private AdService adService;

    @Mock
    private AdRepository repository;

    @Mock
    private AuditSearchService auditSearchService;

    @BeforeEach
    void setUp() {
        adService = new AdService(repository, auditSearchService);
    }

    @Test
    void getAds() {
        // given
        AdFilter filter = Mockito.mock(AdFilter.class);
        Pageable pageable = Mockito.mock(Pageable.class);
        Sort sort = Mockito.mock(Sort.class);
        given(pageable.getSort()).willReturn(sort);

        AdServicePort.SearchCommand command = Mockito.mock(AdServicePort.SearchCommand.class);
        given(command.getPageable()).willReturn(pageable);
        given(command.getFilter()).willReturn(filter);

        List<AdProjection> ads = List.of(Mockito.mock(AdProjection.class));
        Page<AdProjection> page = (Page<AdProjection>) Mockito.mock(Page.class);
        given(page.getContent()).willReturn(ads);
        Long total = 100L;
        given(page.getTotalElements()).willReturn(total);
        given(repository.fetchPaged(filter, pageable)).willReturn(page);

        // when
        SearchResponse result = adService.getAds(command);

        //then
        InOrder inorder = inOrder(auditSearchService, repository, auditSearchService);
        inorder.verify(auditSearchService).auditSearchResults(filter, sort);
        inorder.verify(repository).fetchPaged(filter, pageable);
        inorder.verify(auditSearchService).auditPagedSearchResults(ads, pageable);

        assertEquals(ads, result.getItems());
        assertEquals(total, result.getTotalResults());
    }

    @Test
    void getAd() {
        // given
        String adId = "identifier";
        AdDetailsProjection adDetailsProjection = Mockito.mock(AdDetailsProjection.class);
        given(adDetailsProjection.getAdId()).willReturn(adId);
        given(repository.fetchById(any())).willReturn(adDetailsProjection);
        AdServicePort.GetDetailsCommand command = AdServicePort.GetDetailsCommand.builder()
                .bySearch(true)
                .identifier(adId)
                .build();

        // when
        AdDetailsProjection result = adService.getAd(command);

        InOrder inorder = inOrder(repository, auditSearchService);
        inorder.verify(repository).fetchById(command.getIdentifier());
        inorder.verify(auditSearchService).auditAd(command.getIdentifier(), command.isBySearch());

        assertEquals(adDetailsProjection, result);
    }
}