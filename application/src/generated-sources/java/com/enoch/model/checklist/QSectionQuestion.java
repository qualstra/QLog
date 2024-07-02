package com.enoch.model.checklist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSectionQuestion is a Querydsl query type for SectionQuestion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSectionQuestion extends EntityPathBase<SectionQuestion> {

    private static final long serialVersionUID = -1968595410L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSectionQuestion sectionQuestion = new QSectionQuestion("sectionQuestion");

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuestion question;

    public final QSection section;

    public QSectionQuestion(String variable) {
        this(SectionQuestion.class, forVariable(variable), INITS);
    }

    public QSectionQuestion(Path<? extends SectionQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSectionQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSectionQuestion(PathMetadata metadata, PathInits inits) {
        this(SectionQuestion.class, metadata, inits);
    }

    public QSectionQuestion(Class<? extends SectionQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QQuestion(forProperty("question"), inits.get("question")) : null;
        this.section = inits.isInitialized("section") ? new QSection(forProperty("section"), inits.get("section")) : null;
    }

}

