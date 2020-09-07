package com.wordpress.kkaravitis.ad.search.adapter.inbound.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Getter
@Setter
public class PageParameters implements Serializable {

    private static final long serialVersionUID = 9087528676355176474L;

    private Integer page = 0;

    private Long pageSize;

    private String sortField;

    private Sort.Direction sortDirection;
}
