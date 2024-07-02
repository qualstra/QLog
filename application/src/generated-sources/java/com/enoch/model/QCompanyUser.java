package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyUser is a Querydsl query type for CompanyUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompanyUser extends EntityPathBase<CompanyUser> {

    private static final long serialVersionUID = 669207987L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyUser companyUser = new QCompanyUser("companyUser");

    public final QCompany company;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRank rank;

    public final QShip ship;

    public final QUser user;

    public QCompanyUser(String variable) {
        this(CompanyUser.class, forVariable(variable), INITS);
    }

    public QCompanyUser(Path<? extends CompanyUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyUser(PathMetadata metadata, PathInits inits) {
        this(CompanyUser.class, metadata, inits);
    }

    public QCompanyUser(Class<? extends CompanyUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company"), inits.get("company")) : null;
        this.rank = inits.isInitialized("rank") ? new QRank(forProperty("rank"), inits.get("rank")) : null;
        this.ship = inits.isInitialized("ship") ? new QShip(forProperty("ship"), inits.get("ship")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

