package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoyage is a Querydsl query type for Voyage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVoyage extends EntityPathBase<Voyage> {

    private static final long serialVersionUID = 566843764L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoyage voyage = new QVoyage("voyage");

    public final DateTimePath<java.util.Date> atd = createDateTime("atd", java.util.Date.class);

    public final QAuditTrail auditTrail;

    public final DateTimePath<java.util.Date> checklist_etc = createDateTime("checklist_etc", java.util.Date.class);

    public final QCompany company;

    public final com.enoch.master.model.QPort destination;

    public final DateTimePath<java.util.Date> eta = createDateTime("eta", java.util.Date.class);

    public final DateTimePath<java.util.Date> etd = createDateTime("etd", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.enoch.master.model.QPort orgin;

    public final QShip ship;

    public final EnumPath<com.enoch.constant.VoyageStatus> status = createEnum("status", com.enoch.constant.VoyageStatus.class);

    public final StringPath voyageNo = createString("voyageNo");

    public QVoyage(String variable) {
        this(Voyage.class, forVariable(variable), INITS);
    }

    public QVoyage(Path<? extends Voyage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoyage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoyage(PathMetadata metadata, PathInits inits) {
        this(Voyage.class, metadata, inits);
    }

    public QVoyage(Class<? extends Voyage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company"), inits.get("company")) : null;
        this.destination = inits.isInitialized("destination") ? new com.enoch.master.model.QPort(forProperty("destination"), inits.get("destination")) : null;
        this.orgin = inits.isInitialized("orgin") ? new com.enoch.master.model.QPort(forProperty("orgin"), inits.get("orgin")) : null;
        this.ship = inits.isInitialized("ship") ? new QShip(forProperty("ship"), inits.get("ship")) : null;
    }

}

