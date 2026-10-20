package com.legenkiy.socket;

import com.legenkiy.event.BidPlacedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class BidPlacedEventObserver {

    private final WebSocketEventSender eventSender;

    @ActivateRequestContext
    public void onBidPlaced(@Observes(during = TransactionPhase.AFTER_SUCCESS) BidPlacedEvent event) {
        eventSender.sendBidEvent(event);
    }
}
