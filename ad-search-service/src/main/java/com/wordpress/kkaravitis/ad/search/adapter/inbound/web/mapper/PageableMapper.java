package com.wordpress.kkaravitis.ad.search.adapter.inbound.web.mapper;

import com.wordpress.kkaravitis.ad.search.adapter.inbound.web.model.PageParameters;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PageableMapper {

  public static final Integer ZERO_PAGE = 0;

  public static final Long MAX_RESULTS_PER_PAGE = 10000L;

  public Pageable map(PageParameters params) {
    int page = Optional.ofNullable(params.getPage()).orElse(ZERO_PAGE).intValue();
    int perPage = Optional.ofNullable(params.getPageSize()).orElse(MAX_RESULTS_PER_PAGE).intValue();
    String sortParam = params.getSortField();
    Sort sort = null;
    if(sortParam != null) {
      sort = Sort.by(params.getSortDirection(), sortParam);
    } else {
      sort = Sort.unsorted();
    }
    return PageRequest.of(page, perPage, sort);
  }
}
