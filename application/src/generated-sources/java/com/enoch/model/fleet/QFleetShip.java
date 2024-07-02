package com.enoch.model.fleet;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFleetShip is a Querydsl query type for FleetShip
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFleetShip extends EntityPathBase<FleetShip> {

    private static final long serialVersionUID = -2116207019L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFleetShip fleetShip = new QFleetShip("fleetShip");

    public final QFleet fleet;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.enoch.model.QShip ship;

    public QFleetShip(String variable) {
        this(FleetShip.class, forVariable(variable), INITS);
    }

    public QFleetShip(Path<? extends FleetShip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFleetShip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFleetShip(PathMetadata metadata, PathInits inits) {
        this(FleetShip.class, metadata, inits);
    }

    public QFleetShip(Class<? extends FleetShip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fleet = inits.isInitialized("fleet") ? new QFleet(forProperty("fleet"), inits.get("fleet")) : null;
        this.ship = inits.isInitialized("ship") ? new com.enoch.model.QShip(forProperty("ship"), inits.get("ship")) : null;
    }

}

