package com.legenkiy;

import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.model.Bid;
import com.legenkiy.model.BidStatus;

import java.util.Optional;
import java.util.UUID;

public interface BidService {

    Bid create(MakeBidCommand command);

    Bid markWithStatus(UUID bidId, BidStatus status);

    Optional<Bid> findBidById(UUID bidId);

    Bid getBidById(UUID bidId);

}
