package com.wordpress.kkaravitis.ad.search.infrastructure.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class OptionalPredicateBuilder {
  private final BooleanExpression predicate;

  public <T> OptionalPredicateBuilder notNullAnd(Function<T, BooleanExpression> expressionFunction, T value) {
    return value == null ?
            this :
            new OptionalPredicateBuilder(predicate.and(expressionFunction.apply(value)));
  }

  public BooleanExpression build() {
    return predicate;
  }
}
