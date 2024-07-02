package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRank is a Querydsl query type for Rank
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRank extends EntityPathBase<Rank> {

    private static final long serialVersionUID = 331182785L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRank rank = new QRank("rank");

    public final QAuditTrail auditTrail;

    public final StringPath code = createString("code");

    public final StringPath description = createString("description");

    public final BooleanPath isAvailable = createBoolean("isAvailable");

    public final BooleanPath multiUser = createBoolean("multiUser");

    public final StringPath name = createString("name");

    public QRank(String variable) {
        this(Rank.class, forVariable(variable), INITS);
    }

    public QRank(Path<? extends Rank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRank(PathMetadata metadata, PathInits inits) {
        this(Rank.class, metadata, inits);
    }

    public QRank(Class<? extends Rank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new QAuditTrail(forProperty("auditTrail")) : null;
    }

}

