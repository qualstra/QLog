package com.enoch.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditTrail is a Querydsl query type for AuditTrail
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAuditTrail extends BeanPath<AuditTrail> {

    private static final long serialVersionUID = -1466320448L;

    public static final QAuditTrail auditTrail = new QAuditTrail("auditTrail");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final StringPath updatedBy = createString("updatedBy");

    public final DateTimePath<java.util.Date> updatedDate = createDateTime("updatedDate", java.util.Date.class);

    public QAuditTrail(String variable) {
        super(AuditTrail.class, forVariable(variable));
    }

    public QAuditTrail(Path<? extends AuditTrail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditTrail(PathMetadata metadata) {
        super(AuditTrail.class, metadata);
    }

}

