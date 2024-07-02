package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyRank is a Querydsl query type for CompanyRank
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompanyRank extends EntityPathBase<CompanyRank> {

    private static final long serialVersionUID = 669101588L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyRank companyRank = new QCompanyRank("companyRank");

    public final QCompany company;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRank rank;

    public QCompanyRank(String variable) {
        this(CompanyRank.class, forVariable(variable), INITS);
    }

    public QCompanyRank(Path<? extends CompanyRank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyRank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyRank(PathMetadata metadata, PathInits inits) {
        this(CompanyRank.class, metadata, inits);
    }

    public QCompanyRank(Class<? extends CompanyRank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company"), inits.get("company")) : null;
        this.rank = inits.isInitialized("rank") ? new QRank(forProperty("rank"), inits.get("rank")) : null;
    }

}

