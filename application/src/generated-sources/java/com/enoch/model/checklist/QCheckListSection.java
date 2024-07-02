package com.enoch.model.checklist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckListSection is a Querydsl query type for CheckListSection
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCheckListSection extends EntityPathBase<CheckListSection> {

    private static final long serialVersionUID = 317536860L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckListSection checkListSection = new QCheckListSection("checkListSection");

    public final QCheckList checklist;

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSection section;

    public QCheckListSection(String variable) {
        this(CheckListSection.class, forVariable(variable), INITS);
    }

    public QCheckListSection(Path<? extends CheckListSection> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckListSection(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckListSection(PathMetadata metadata, PathInits inits) {
        this(CheckListSection.class, metadata, inits);
    }

    public QCheckListSection(Class<? extends CheckListSection> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.checklist = inits.isInitialized("checklist") ? new QCheckList(forProperty("checklist"), inits.get("checklist")) : null;
        this.section = inits.isInitialized("section") ? new QSection(forProperty("section"), inits.get("section")) : null;
    }

}

