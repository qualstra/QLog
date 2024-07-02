package com.enoch.model.checklist.inst;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckListInst is a Querydsl query type for CheckListInst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCheckListInst extends EntityPathBase<CheckListInst> {

    private static final long serialVersionUID = -808046559L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckListInst checkListInst = new QCheckListInst("checkListInst");

    public final NumberPath<Integer> ansQuesCount = createNumber("ansQuesCount", Integer.class);

    public final StringPath assocTo = createString("assocTo");

    public final EnumPath<Association> assocType = createEnum("assocType", Association.class);

    public final com.enoch.model.QAuditTrail auditTrail;

    public final ComparablePath<java.util.UUID> checkId = createComparable("checkId", java.util.UUID.class);

    public final com.enoch.model.QCompany company;

    public final DateTimePath<java.util.Date> completedDate = createDateTime("completedDate", java.util.Date.class);

    public final StringPath data = createString("data");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath longDesc = createString("longDesc");

    public final ComparablePath<java.util.UUID> mChk = createComparable("mChk", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final StringPath postProcessor = createString("postProcessor");

    public final StringPath preProcessor = createString("preProcessor");

    public final NumberPath<Integer> quesCount = createNumber("quesCount", Integer.class);

    public final StringPath shortDesc = createString("shortDesc");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final EnumPath<State> state = createEnum("state", State.class);

    public final StringPath type = createString("type");

    public final com.enoch.model.QShip vessel;

    public QCheckListInst(String variable) {
        this(CheckListInst.class, forVariable(variable), INITS);
    }

    public QCheckListInst(Path<? extends CheckListInst> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckListInst(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckListInst(PathMetadata metadata, PathInits inits) {
        this(CheckListInst.class, metadata, inits);
    }

    public QCheckListInst(Class<? extends CheckListInst> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new com.enoch.model.QCompany(forProperty("company"), inits.get("company")) : null;
        this.vessel = inits.isInitialized("vessel") ? new com.enoch.model.QShip(forProperty("vessel"), inits.get("vessel")) : null;
    }

}

