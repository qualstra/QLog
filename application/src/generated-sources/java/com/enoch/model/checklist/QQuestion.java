package com.enoch.model.checklist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = 1670708483L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestion question = new QQuestion("question");

    public final BooleanPath active = createBoolean("active");

    public final com.enoch.model.QAuditTrail auditTrail;

    public final com.enoch.model.QCompany company;

    public final StringPath data = createString("data");

    public final StringPath filter = createString("filter");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final ComparablePath<java.util.UUID> parentUUId = createComparable("parentUUId", java.util.UUID.class);

    public final StringPath postProcessor = createString("postProcessor");

    public final StringPath preProcessor = createString("preProcessor");

    public final NumberPath<Integer> queId = createNumber("queId", Integer.class);

    public final StringPath questionHelp = createString("questionHelp");

    public final StringPath questionText = createString("questionText");

    public final NumberPath<Integer> serNo = createNumber("serNo", Integer.class);

    public final EnumPath<State> state = createEnum("state", State.class);

    public final EnumPath<QuestionType> type = createEnum("type", QuestionType.class);

    public final ComparablePath<java.util.UUID> uuid = createComparable("uuid", java.util.UUID.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QQuestion(String variable) {
        this(Question.class, forVariable(variable), INITS);
    }

    public QQuestion(Path<? extends Question> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestion(PathMetadata metadata, PathInits inits) {
        this(Question.class, metadata, inits);
    }

    public QQuestion(Class<? extends Question> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditTrail = inits.isInitialized("auditTrail") ? new com.enoch.model.QAuditTrail(forProperty("auditTrail")) : null;
        this.company = inits.isInitialized("company") ? new com.enoch.model.QCompany(forProperty("company"), inits.get("company")) : null;
    }

}

