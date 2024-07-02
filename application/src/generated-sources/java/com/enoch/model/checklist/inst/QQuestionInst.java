package com.enoch.model.checklist.inst;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionInst is a Querydsl query type for QuestionInst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuestionInst extends EntityPathBase<QuestionInst> {

    private static final long serialVersionUID = 511483319L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionInst questionInst = new QQuestionInst("questionInst");

    public final StringPath answer = createString("answer");

    public final StringPath answerBy = createString("answerBy");

    public final DateTimePath<java.util.Date> answerDate = createDateTime("answerDate", java.util.Date.class);

    public final StringPath assocTo = createString("assocTo");

    public final EnumPath<Association> assocType = createEnum("assocType", Association.class);

    public final com.enoch.model.QAuditTrail auditTrail;

    public final com.enoch.model.QCompany company;

    public final StringPath data = createString("data");

    public final StringPath filter = createString("filter");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> masterParentUUID = createComparable("masterParentUUID", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> masterUUID = createComparable("masterUUID", java.util.UUID.class);

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final ComparablePath<java.util.UUID> parentUUID = createComparable("parentUUID", java.util.UUID.class);

    public final StringPath postProcessor = createString("postProcessor");

    public final StringPath preProcessor = createString("preProcessor");

    public final StringPath questionHelp = createString("questionHelp");

    public final StringPath questionText = createString("questionText");

    public final NumberPath<Integer> serNo = createNumber("serNo", Integer.class);

    public final EnumPath<com.enoch.model.checklist.QuestionType> type = createEnum("type", com.enoch.model.checklist.QuestionType.class);

    public final ComparablePath<java.util.UUID> UUID = createComparable("UUID", java.util.UUID.class);

    public final com.enoch.model.QShip vessel;

    public QQuestionInst(String variable) {
        this(QuestionInst.class, forVariable(variable), INITS);
    }

    public QQuestionInst(Path<? extends QuestionInst> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionInst(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionInst(PathMetadata metadata, PathInits inits) {
        this(QuestionInst.class, metadata, inits);
    }

    public QQuestionInst(Class<? extends QuestionInst> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new com.enoch.model.QCompany(forProperty("company"), inits.get("company")) : null;
        this.vessel = inits.isInitialized("vessel") ? new com.enoch.model.QShip(forProperty("vessel"), inits.get("vessel")) : null;
    }

}

