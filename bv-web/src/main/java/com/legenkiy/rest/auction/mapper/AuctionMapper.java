package com.legenkiy.rest.auction.mapper;

import com.legenkiy.rest.auction.model.CreateAuctionRq;
import com.legenkiy.dto.CreateAuctionCommand;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AuctionMapper {

    public CreateAuctionCommand toCreateCommand(CreateAuctionRq rq, UUID currentUserId) {
        return new CreateAuctionCommand(
                rq.title(),
                rq.description(),
                rq.startPrice(),
                rq.duration(),
                currentUserId
        );
    }

}
