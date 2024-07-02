package com.enoch.model.checklist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckList is a Querydsl query type for CheckList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCheckList extends EntityPathBase<CheckList> {

    private static final long serialVersionUID = -1862031287L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckList checkList = new QCheckList("checkList");

    public final BooleanPath active = createBoolean("active");

    public final com.enoch.model.QAuditTrail auditTrail;

    public final ComparablePath<java.util.UUID> checkId = createComparable("checkId", java.util.UUID.class);

    public final com.enoch.model.QCompany company;

    public final StringPath data = createString("data");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath longDesc = createString("longDesc");

    public final StringPath name = createString("name");

    public final StringPath postProcessor = createString("postProcessor");

    public final StringPath preProcessor = createString("preProcessor");

    public final StringPath remarks = createString("remarks");

    public final StringPath shortDesc = createString("shortDesc");

    public final EnumPath<State> state = createEnum("state", State.class);

    public final EnumPath<ChecklistType> type = createEnum("type", ChecklistType.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QCheckList(String variable) {
        this(CheckList.class, forVariable(variable), INITS);
    }

    public QCheckList(Path<? extends CheckList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckList(PathMetadata metadata, PathInits inits) {
        this(CheckList.class, metadata, inits);
    }

    public QCheckList(Class<? extends CheckList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new com.enoch.model.QCompany(forProperty("company"), inits.get("company")) : null;
    }

}

