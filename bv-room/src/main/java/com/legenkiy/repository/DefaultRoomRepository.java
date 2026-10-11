package com.legenkiy.repository;

import com.legenkiy.model.RoomEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DefaultRoomRepository implements PanacheRepositoryBase<RoomEntity, UUID> {

}
