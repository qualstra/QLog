package com.enoch.model.checklist.inst;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSectionQuestionInst is a Querydsl query type for SectionQuestionInst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSectionQuestionInst extends EntityPathBase<SectionQuestionInst> {

    private static final long serialVersionUID = -489034234L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSectionQuestionInst sectionQuestionInst = new QSectionQuestionInst("sectionQuestionInst");

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuestionInst question;

    public final QSectionInst section;

    public QSectionQuestionInst(String variable) {
        this(SectionQuestionInst.class, forVariable(variable), INITS);
    }

    public QSectionQuestionInst(Path<? extends SectionQuestionInst> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSectionQuestionInst(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSectionQuestionInst(PathMetadata metadata, PathInits inits) {
        this(SectionQuestionInst.class, metadata, inits);
    }

    public QSectionQuestionInst(Class<? extends SectionQuestionInst> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QQuestionInst(forProperty("question"), inits.get("question")) : null;
        this.section = inits.isInitialized("section") ? new QSectionInst(forProperty("section"), inits.get("section")) : null;
    }

}

