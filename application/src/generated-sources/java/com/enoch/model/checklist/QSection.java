package com.enoch.model.checklist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSection is a Querydsl query type for Section
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSection extends EntityPathBase<Section> {

    private static final long serialVersionUID = 1923196456L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSection section = new QSection("section");

    public final BooleanPath active = createBoolean("active");

    public final com.enoch.model.QAuditTrail auditTrail;

    public final com.enoch.model.QCompany company;

    public final StringPath data = createString("data");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath longDescription = createString("longDescription");

    public final StringPath name = createString("name");

    public final StringPath postProcessor = createString("postProcessor");

    public final StringPath preProcessor = createString("preProcessor");

    public final StringPath shortDesc = createString("shortDesc");

    public final NumberPath<Integer> slNo = createNumber("slNo", Integer.class);

    public final EnumPath<State> state = createEnum("state", State.class);

    public final ComparablePath<java.util.UUID> UUID = createComparable("UUID", java.util.UUID.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QSection(String variable) {
        this(Section.class, forVariable(variable), INITS);
    }

    public QSection(Path<? extends Section> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSection(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSection(PathMetadata metadata, PathInits inits) {
        this(Section.class, metadata, inits);
    }

    public QSection(Class<? extends Section> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new com.enoch.model.QCompany(forProperty("company"), inits.get("company")) : null;
    }

}

