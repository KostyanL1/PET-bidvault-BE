package com.legenkiy;

import com.legenkiy.dto.CreateAuctionCommand;
import com.legenkiy.dto.UpdateAuctionCommand;
import com.legenkiy.model.Auction;
import com.legenkiy.model.AuctionStatus;

import java.util.Optional;
import java.util.UUID;

public interface AuctionService {

    Auction create(CreateAuctionCommand command);

    Auction markAuctionWithStatus(UUID auctionId, AuctionStatus status);

    Auction update(UpdateAuctionCommand command);

    int increaseCountOfParticipants(UUID auctionId);

    Auction getById(UUID auctionId);

    Optional<Auction> findById(UUID auctionId);

    void deleteById(UUID auctionId);
}
