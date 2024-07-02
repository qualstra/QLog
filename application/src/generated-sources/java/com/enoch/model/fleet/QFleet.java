package com.enoch.model.fleet;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFleet is a Querydsl query type for Fleet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFleet extends EntityPathBase<Fleet> {

    private static final long serialVersionUID = 177656953L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFleet fleet = new QFleet("fleet");

    public final com.enoch.model.QAuditTrail auditTrail;

    public final com.enoch.model.QCompany company;

    public final com.enoch.model.QUser fleetManager;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ComparablePath<java.util.UUID> uuid = createComparable("uuid", java.util.UUID.class);

    public QFleet(String variable) {
        this(Fleet.class, forVariable(variable), INITS);
    }

    public QFleet(Path<? extends Fleet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFleet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFleet(PathMetadata metadata, PathInits inits) {
        this(Fleet.class, metadata, inits);
    }

    public QFleet(Class<? extends Fleet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new com.enoch.model.QCompany(forProperty("company"), inits.get("company")) : null;
        this.fleetManager = inits.isInitialized("fleetManager") ? new com.enoch.model.QUser(forProperty("fleetManager"), inits.get("fleetManager")) : null;
    }

}

