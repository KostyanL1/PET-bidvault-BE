package com.legenkiy.socket;

import com.legenkiy.BidService;
import com.legenkiy.event.BidPlacedEvent;
import com.legenkiy.model.BidStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class BidPlacedEventObserver {

    private final WebSocketEventSender eventSender;
    private final BidService bidService;

    @ActivateRequestContext
    public void onBidPlaced(@Observes(during = TransactionPhase.AFTER_SUCCESS) BidPlacedEvent event) {
        bidService.markWithStatus(event.bidId(), BidStatus.SETTLED);
        log.info("Publishing event BidPlacedEvent: bidId = {}, bidStatus = {}", event.bidId(), BidStatus.SETTLED);
        eventSender.sendBidEvent(event);
    }
}
