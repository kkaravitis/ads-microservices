package com.wordpress.kkaravitis.ad.search.adapter.inbound.web;

import com.wordpress.kkaravitis.ad.search.adapter.inbound.web.mapper.AdFilterMapper;
import com.wordpress.kkaravitis.ad.search.adapter.inbound.web.mapper.PageableMapper;
import com.wordpress.kkaravitis.ad.search.adapter.inbound.web.model.PageParameters;
import com.wordpress.kkaravitis.ad.search.adapter.inbound.web.model.SearchCriteriaParameters;
import com.wordpress.kkaravitis.ad.search.application.domain.Ad;
import com.wordpress.kkaravitis.ad.search.application.domain.AdDetailsProjection;
import com.wordpress.kkaravitis.ad.search.application.domain.AdFilter;
import com.wordpress.kkaravitis.ad.search.application.domain.SearchResponse;
import com.wordpress.kkaravitis.ad.search.application.port.inbound.AdServicePort;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/kkaravitis")
public class SearchController {
    private static final Integer ZERO_PAGE = 0;
    private static final Integer MAX_RESULTS_PER_PAGE = 100;

    private AdServicePort adService;
    private PageableMapper pageableMapper;
    private AdFilterMapper adFilterMapper;

    @ApiOperation(value = "Searches by free text and returns the ads of the page. \n" +
            "If you do not pass the page number (page param) then returns the first page (page = 0)" + ") of results." +
            "If you do not pass the size of the results page (perPage params) then returns at most 100 ads per page",
            response = SearchResponse.class)
    @GetMapping("/ads")
    public SearchResponse getAds(PageParameters pageParams, SearchCriteriaParameters criteriaParams) {
        Pageable pageable = pageableMapper.map(pageParams);
        AdFilter adFilter = adFilterMapper.map(criteriaParams);
        return adService.getAds(AdServicePort.SearchCommand.builder()
                .pageable(pageable)
                .filter(adFilter)
                .build());
    }

    @ApiOperation(value = "Returns an ad by id. \n" +
            "If the request is originated by clicking on an ad displayed in search results then " +
            "the request must include the FROM_SEARCH_RESULTS request header with any value. " +
            "If the FROM_SEARCH_RESULTS request header is undefined then the tracking system assumes " +
            "that the user accessed directly the ad \n" +
            "(by following a link in an email for example)", response = Ad.class)
    @GetMapping("/ad/{id}")
    public ResponseEntity<AdDetailsProjection> getAd(@PathVariable("id") String id,
                                    @RequestHeader(name = "FROM_SEARCH_RESULTS", required = false) String header) {
        boolean bySearch = header != null;
        // TODO build the command and pass it to the service
        AdDetailsProjection ad = adService.getAd(AdServicePort.GetDetailsCommand.builder()
                .bySearch(bySearch)
                        .identifier(id)
                .build());

        if (ad != null) {
            return new ResponseEntity<>(ad, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
