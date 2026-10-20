package com.legenkiy.socket;

import com.legenkiy.event.BidPlacedEvent;
import com.legenkiy.socket.mapper.WebSocketResponseMapper;
import io.quarkus.websockets.next.OpenConnections;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class WebSocketEventSender {

    private final WebSocketResponseMapper mapper;
    private final OpenConnections openConnections;

    public void sendBidEvent(BidPlacedEvent bid) {
        String roomId = bid.roomId().toString();
        openConnections.findByEndpointId(AuctionWebSocket.ENDPOINT_ID).stream()
                .filter(connection -> roomId.equals(connection.pathParam("roomId")))
                .findFirst()
                .ifPresent(connection -> connection.broadcast()
                        .filter(c -> roomId.equals(c.pathParam("roomId")))
                        .sendText(mapper.toBidSettleEvent(bid))
                        .subscribe()
                        .with(
                                ignore -> {},
                                failure -> log.error("Failed to broadcast event", failure)
                        ));
    }

}
