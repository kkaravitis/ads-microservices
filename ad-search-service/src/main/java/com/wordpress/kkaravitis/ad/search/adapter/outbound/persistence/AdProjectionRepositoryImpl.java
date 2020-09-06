package com.wordpress.kkaravitis.ad.search.adapter.outbound.persistence;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.wordpress.kkaravitis.ad.search.application.*;
import com.wordpress.kkaravitis.ad.search.infrastructure.util.OptionalPredicateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

public class AdProjectionRepositoryImpl implements AdProjectionRepository {
    private final QAd ad = QAd.ad;
    private final QCategory category = QCategory.category;
    private final QCustomer customer = QCustomer.customer;

    private final Map<String, Expression<?>> sortingMap = Stream.of(new Object[][]{
            {Constants.AD_PRICE, ad.price},
            {Constants.AD_CATEGORY, category.identifier},
            {Constants.AD_CREATED_DATE, ad.createdAt},
            {Constants.CREATOR_NAME, customer.name},
            {Constants.CREATOR_ROLE, customer.role}
    }).collect(Collectors.toMap(
            data -> (String) data[0],
            data -> (EntityPathBase<?>) data[1]));

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<AdProjection> fetchAll(AdFilter filter, Sort sort) {
        JPAQuery<AdProjection> query = getSearchQuery(filter);
        sort.forEach(order -> this.applyOrder(order, query));
        return query.fetch();
    }

    @Override
    public Page<AdProjection> fetchPaged(AdFilter filter, Pageable pageable) {
        JPAQuery<AdProjection> query = getSearchQuery(filter);
        if (pageable.isPaged()) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());
        }
        pageable.getSort().forEach(order -> this.applyOrder(order, query));
        return PageableExecutionUtils.getPage(query.fetch(), pageable, query::fetchCount);
    }

    @Override
    public AdDetailsProjection fetchById(String adId) {
        return new JPAQuery<AdDetailsProjection>(entityManager).select(new QAdDetailsProjection(
                ad.id,
                ad.description,
                customer.name,
                customer.role,
                ad.price,
                ad.createdAt
        )).from(ad).join(ad.customer, customer)
                .on(ad.id.eq(adId)).fetchOne();
    }

    private JPAQuery<AdProjection> getSearchQuery(AdFilter filter) {
        return new JPAQuery<AdProjection>(entityManager)
                .select(new QAdProjection(ad.id, ad.shortDescription, ad.price, customer.name))
                .from(ad).join(ad.category, category)
                .on(new OptionalPredicateBuilder(ad.isNotNull())
                        .notNullAnd(category.identifier::eq, filter.getCategoryIdentifier()).build())
                .join(ad.customer, customer)
                .on(new OptionalPredicateBuilder(customer.isNotNull())
                        .notNullAnd(customer.name::containsIgnoreCase, filter.getCustomerName())
                        .notNullAnd(customer.role::eq, filter.getCustomerRole())
                        .build())
                .where(new OptionalPredicateBuilder(ad.isNotNull())
                        .notNullAnd(ad.description::containsIgnoreCase, filter.getTerm())
                        .build());
    }

    private void applyOrder(Sort.Order order, JPAQuery<AdProjection> query) {
        String property = order.getProperty();
        Expression<?> expression = sortingMap.get(property);
        if (expression == null) {
            return;
        }
        Order orderDirection = order.isAscending() ? ASC : DESC;
        query.orderBy(new OrderSpecifier(orderDirection, expression));
    }
}
