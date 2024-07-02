package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMou is a Querydsl query type for Mou
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMou extends EntityPathBase<Mou> {

    private static final long serialVersionUID = 1950341598L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMou mou = new QMou("mou");

    public final QAuditTrail auditTrail;

    public final NumberPath<Long> cicid = createNumber("cicid", Long.class);

    public final StringPath code = createString("code");

    public final StringPath desc = createString("desc");

    public final DateTimePath<java.util.Date> fromDate = createDateTime("fromDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> toDate = createDateTime("toDate", java.util.Date.class);

    public QMou(String variable) {
        this(Mou.class, forVariable(variable), INITS);
    }

    public QMou(Path<? extends Mou> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMou(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMou(PathMetadata metadata, PathInits inits) {
        this(Mou.class, metadata, inits);
    }

    public QMou(Class<? extends Mou> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new QAuditTrail(forProperty("auditTrail")) : null;
    }

}

