package com.legenkiy.rest.auction.model;

import java.util.UUID;

public record BidRq(
        UUID auctionId,
        double amount
) {
}
