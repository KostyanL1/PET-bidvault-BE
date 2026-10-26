package com.legenkiy.repository;

import com.legenkiy.model.RoomEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DefaultRoomRepository implements PanacheRepositoryBase<RoomEntity, UUID> {

    private static final String GET_AUCTION_ID_BY_ROOM_ID_QUERY = """
            SELECT r.auctionId
            FROM RoomEntity r
            WHERE r.id = :roomId
            """;

    public UUID getAuctionIdByRoomId(UUID roomId) {
        return getEntityManager()
                .createQuery(GET_AUCTION_ID_BY_ROOM_ID_QUERY, UUID.class)
                .setParameter("roomId", roomId)
                .getSingleResult();
    }
}
