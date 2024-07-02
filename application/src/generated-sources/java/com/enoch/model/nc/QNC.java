package com.enoch.model.nc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNC is a Querydsl query type for NC
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNC extends EntityPathBase<NC> {

    private static final long serialVersionUID = 1702587577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNC nC = new QNC("nC");

    public final com.enoch.model.QAuditTrail auditTrail;

    public final ComparablePath<java.util.UUID> checkId = createComparable("checkId", java.util.UUID.class);

    public final DateTimePath<java.util.Date> closureTime = createDateTime("closureTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> creationTime = createDateTime("creationTime", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath level = createString("level");

    public final com.enoch.model.checklist.inst.QQuestionInst question;

    public final StringPath remarks = createString("remarks");

    public final StringPath responsible = createString("responsible");

    public final com.enoch.model.QShip ship;

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final ComparablePath<java.util.UUID> UUID = createComparable("UUID", java.util.UUID.class);

    public QNC(String variable) {
        this(NC.class, forVariable(variable), INITS);
    }

    public QNC(Path<? extends NC> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNC(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNC(PathMetadata metadata, PathInits inits) {
        this(NC.class, metadata, inits);
    }

    public QNC(Class<? extends NC> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.question = inits.isInitialized("question") ? new com.enoch.model.checklist.inst.QQuestionInst(forProperty("question"), inits.get("question")) : null;
        this.ship = inits.isInitialized("ship") ? new com.enoch.model.QShip(forProperty("ship"), inits.get("ship")) : null;
    }

}

