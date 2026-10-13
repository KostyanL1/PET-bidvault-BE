package com.legenkiy.socket.model;

import io.quarkus.websockets.next.WebSocketConnection;

import java.util.UUID;

public record RoomConnection(
        UUID roomId,
        UUID userId,
        WebSocketConnection connection
) {
}
