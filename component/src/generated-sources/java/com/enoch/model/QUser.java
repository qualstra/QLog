package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 331289184L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QAuditTrail auditTrial;

    public final StringPath cdc = createString("cdc");

    public final DateTimePath<java.util.Date> dataExportDate = createDateTime("dataExportDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> dob = createDateTime("dob", java.util.Date.class);

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final StringPath lastName = createString("lastName");

    public final StringPath name = createString("name");

    public final StringPath passport = createString("passport");

    public final StringPath password = createString("password");

    public final QRank rank;

    public final NumberPath<Integer> reportingTo = createNumber("reportingTo", Integer.class);

    public final StringPath userName = createString("userName");

    public final ComparablePath<java.util.UUID> UUID = createComparable("UUID", java.util.UUID.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrial = inits.isInitialized("auditTrial") ? new QAuditTrail(forProperty("auditTrial")) : null;
        this.rank = inits.isInitialized("rank") ? new QRank(forProperty("rank"), inits.get("rank")) : null;
    }

}

