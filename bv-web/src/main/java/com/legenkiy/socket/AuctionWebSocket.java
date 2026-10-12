package com.legenkiy.socket;

import com.legenkiy.socket.model.BaseEventModel;
import com.legenkiy.socket.model.EventType;
import com.legenkiy.socket.model.UserJoinedEventModel;
import com.legenkiy.security.socket.SocketAuthService;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@WebSocket(path = "/auction/{roomId}")
public class AuctionWebSocket {

    private final WebSocketConnection connection;
    private final SocketAuthService socketAuthService;

    @OnOpen
    public BaseEventModel onOpen() {
        log.info("User trying connected to room: roomId={}", connection.pathParam("roomId"));
        String username = socketAuthService.authenticate(connection);
        log.info("User {} connected", username);


        return UserJoinedEventModel.builder()
                .type(EventType.USER_JOINED).build();
    }

}
