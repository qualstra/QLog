package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCurrency is a Querydsl query type for Currency
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCurrency extends EntityPathBase<Currency> {

    private static final long serialVersionUID = 1367655462L;

    public static final QCurrency currency = new QCurrency("currency");

    public final StringPath code = createString("code");

    public final NumberPath<java.math.BigDecimal> customsexchangeRate = createNumber("customsexchangeRate", java.math.BigDecimal.class);

    public final StringPath description = createString("description");

    public final NumberPath<java.math.BigDecimal> exchangeRate = createNumber("exchangeRate", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> fromDate = createDateTime("fromDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<java.math.BigDecimal> maxLimit = createNumber("maxLimit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> minLimit = createNumber("minLimit", java.math.BigDecimal.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> tenantId = createNumber("tenantId", Integer.class);

    public final DateTimePath<java.util.Date> toDate = createDateTime("toDate", java.util.Date.class);

    public QCurrency(String variable) {
        super(Currency.class, forVariable(variable));
    }

    public QCurrency(Path<? extends Currency> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCurrency(PathMetadata metadata) {
        super(Currency.class, metadata);
    }

}

