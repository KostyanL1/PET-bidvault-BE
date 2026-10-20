package com.legenkiy.socket;

import com.legenkiy.socket.mapper.WebSocketResponseMapper;
import com.legenkiy.socket.model.BaseEventModel;
import com.legenkiy.security.socket.SocketAuthService;
import com.legenkiy.rest.user.utils.ResourcesUtils;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@WebSocket(path = "/auction/{roomId}", endpointId = AuctionWebSocket.ENDPOINT_ID)
public class AuctionWebSocket {

    public static final String ENDPOINT_ID = "auction-room";

    private final SocketAuthService socketAuthService;
    private final ConnectionManager connectionManager;
    private final WebSocketResponseMapper mapper;

    @OnOpen
    public BaseEventModel onOpen(WebSocketConnection connection) {
        UUID roomId = UUID.fromString(connection.pathParam("roomId"));
        log.info("User trying connected to room: roomId={}", roomId);

        JsonWebToken jsonWebToken = socketAuthService.authenticate(connection);
        UUID userId = ResourcesUtils.extractPrincipalId(jsonWebToken);

        connectionManager.saveConnection(roomId, userId, connection);
        log.info("User {} connected", userId);
        return mapper.toUserJoinedEvent(roomId, userId);
    }

}
