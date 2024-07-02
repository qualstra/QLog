package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShip is a Querydsl query type for Ship
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShip extends EntityPathBase<Ship> {

    private static final long serialVersionUID = 331219153L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShip ship = new QShip("ship");

    public final DateTimePath<java.util.Date> activesince = createDateTime("activesince", java.util.Date.class);

    public final QAuditTrail auditTrial;

    public final StringPath callSign = createString("callSign");

    public final StringPath calss = createString("calss");

    public final QCompany company;

    public final StringPath email = createString("email");

    public final StringPath flag = createString("flag");

    public final StringPath grt = createString("grt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imo = createString("imo");

    public final StringPath name = createString("name");

    public final StringPath status = createString("status");

    public final ComparablePath<java.util.UUID> UUID = createComparable("UUID", java.util.UUID.class);

    public final StringPath vesselType = createString("vesselType");

    public QShip(String variable) {
        this(Ship.class, forVariable(variable), INITS);
    }

    public QShip(Path<? extends Ship> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShip(PathMetadata metadata, PathInits inits) {
        this(Ship.class, metadata, inits);
    }

    public QShip(Class<? extends Ship> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrial = inits.isInitialized("auditTrial") ? new QAuditTrail(forProperty("auditTrial")) : null;
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company"), inits.get("company")) : null;
    }

}

