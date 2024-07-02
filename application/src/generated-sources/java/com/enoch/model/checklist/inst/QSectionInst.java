package com.enoch.model.checklist.inst;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSectionInst is a Querydsl query type for SectionInst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSectionInst extends EntityPathBase<SectionInst> {

    private static final long serialVersionUID = -1620182272L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSectionInst sectionInst = new QSectionInst("sectionInst");

    public final NumberPath<Integer> ansQuesCount = createNumber("ansQuesCount", Integer.class);

    public final com.enoch.model.QAuditTrail auditTrail;

    public final com.enoch.model.QCompany company;

    public final StringPath data = createString("data");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> masterUUID = createComparable("masterUUID", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> quesCount = createNumber("quesCount", Integer.class);

    public final NumberPath<Integer> slNo = createNumber("slNo", Integer.class);

    public final ComparablePath<java.util.UUID> UUID = createComparable("UUID", java.util.UUID.class);

    public final com.enoch.model.QShip vessel;

    public QSectionInst(String variable) {
        this(SectionInst.class, forVariable(variable), INITS);
    }

    public QSectionInst(Path<? extends SectionInst> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSectionInst(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSectionInst(PathMetadata metadata, PathInits inits) {
        this(SectionInst.class, metadata, inits);
    }

    public QSectionInst(Class<? extends SectionInst> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new com.enoch.model.QCompany(forProperty("company"), inits.get("company")) : null;
        this.vessel = inits.isInitialized("vessel") ? new com.enoch.model.QShip(forProperty("vessel"), inits.get("vessel")) : null;
    }

}

