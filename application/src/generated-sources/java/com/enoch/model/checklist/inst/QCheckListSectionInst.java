package com.enoch.model.checklist.inst;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckListSectionInst is a Querydsl query type for CheckListSectionInst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCheckListSectionInst extends EntityPathBase<CheckListSectionInst> {

    private static final long serialVersionUID = -604579696L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckListSectionInst checkListSectionInst = new QCheckListSectionInst("checkListSectionInst");

    public final QCheckListInst checklist;

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSectionInst section;

    public QCheckListSectionInst(String variable) {
        this(CheckListSectionInst.class, forVariable(variable), INITS);
    }

    public QCheckListSectionInst(Path<? extends CheckListSectionInst> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckListSectionInst(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckListSectionInst(PathMetadata metadata, PathInits inits) {
        this(CheckListSectionInst.class, metadata, inits);
    }

    public QCheckListSectionInst(Class<? extends CheckListSectionInst> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.checklist = inits.isInitialized("checklist") ? new QCheckListInst(forProperty("checklist"), inits.get("checklist")) : null;
        this.section = inits.isInitialized("section") ? new QSectionInst(forProperty("section"), inits.get("section")) : null;
    }

}

