package com.enoch.master.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPort is a Querydsl query type for Port
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPort extends EntityPathBase<Port> {

    private static final long serialVersionUID = -837864830L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPort port = new QPort("port");

    public final StringPath city = createString("city");

    public final StringPath code = createString("code");

    public final QCountry country;

    public final StringPath description = createString("description");

    public QPort(String variable) {
        this(Port.class, forVariable(variable), INITS);
    }

    public QPort(Path<? extends Port> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPort(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPort(PathMetadata metadata, PathInits inits) {
        this(Port.class, metadata, inits);
    }

    public QPort(Class<? extends Port> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country"), inits.get("country")) : null;
    }

}

