package com.legenkiy.socket;

import com.legenkiy.RoomService;
import com.legenkiy.exception.NotFoundException;
import com.legenkiy.socket.model.RoomConnection;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@RequiredArgsConstructor
public class ConnectionManager {

    private final Map<UUID, Set<RoomConnection>> connectionsHolder = new ConcurrentHashMap<>();

    private final RoomService roomService;

    @ActivateRequestContext
    public RoomConnection saveConnection(UUID roomId, UUID userId, WebSocketConnection connection) {
        roomService.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room with roomId=%s doesn't exist".formatted(roomId)));

        RoomConnection roomConnection = new RoomConnection(roomId, userId, connection);
        return put(roomId, roomConnection);
    }

    public Set<RoomConnection> getAllConnectionByRoomId(UUID roomId) {
        return connectionsHolder.getOrDefault(roomId, Collections.emptySet());
    }

    public WebSocketConnection removeConnection(UUID roomId, UUID userId) {
        Set<RoomConnection> roomConnections = getAllConnectionByRoomId(roomId);

        RoomConnection roomConnection = roomConnections.stream()
                .filter(c -> c.userId().equals(userId))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException("Connection userId=%s in roomId=%s not found".formatted(userId, roomId)));

        roomConnections.remove(roomConnection);

        if (roomConnections.isEmpty()) {
            connectionsHolder.remove(roomId);
        }

        return roomConnection.connection();
    }

    public void forceCloseConnection(UUID roomId, UUID userId) {
        Set<RoomConnection> roomConnections = getAllConnectionByRoomId(roomId);

        WebSocketConnection connection = removeConnection(roomId, userId);

        connection.closeAndAwait();
    }

    public void closeRoom(UUID roomId) {
        Set<RoomConnection> connections = connectionsHolder.remove(roomId);

        if (connections == null) {
            return;
        }

        connections.forEach(roomConnection ->
                roomConnection.connection().closeAndAwait()
        );
    }

    public boolean isConnectionExist(UUID roomId, UUID userId) {
        Set<RoomConnection> connectionsByRoom = connectionsHolder.get(roomId);
        Optional<RoomConnection> roomConnection = connectionsByRoom.stream()
                .filter(rc -> userId.equals(rc.userId()))
                .findFirst();
        return roomConnection.isPresent();
    }

    private RoomConnection put(UUID key, RoomConnection connection) {
        connectionsHolder
                .computeIfAbsent(key, id -> ConcurrentHashMap.newKeySet())
                .add(connection);

        return connection;
    }
}
