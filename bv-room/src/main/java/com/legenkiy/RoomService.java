package com.legenkiy;

import com.legenkiy.model.Room;
import com.legenkiy.model.RoomStatus;

import java.util.Optional;
import java.util.UUID;

public interface RoomService {

    Room create(UUID auctionId);

    Room markRoomWithStatus(UUID roomId, RoomStatus status);

    Room getById(UUID roomId);

    Optional<Room> findById(UUID roomId);

    void deleteById(UUID roomId);
}
