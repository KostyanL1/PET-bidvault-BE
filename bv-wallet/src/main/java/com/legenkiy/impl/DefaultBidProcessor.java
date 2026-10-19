package com.legenkiy.impl;

import com.legenkiy.BidProcessor;
import com.legenkiy.BidService;
import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.event.BidPlacedEvent;
import com.legenkiy.model.Bid;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DefaultBidProcessor implements BidProcessor {

    private final BidService bidService;
    private final Event<BidPlacedEvent> bidPlacedEvent;

    @Override
    @Transactional
    public void makeBid(MakeBidCommand command) {
        Bid bid = bidService.create(command);
        bidPlacedEvent.fire(new BidPlacedEvent(
                bid.getId(), bid.getRoomId(), bid.getUserId(), bid.getAmount(), bid.getCreatedAt()));
    }
}
