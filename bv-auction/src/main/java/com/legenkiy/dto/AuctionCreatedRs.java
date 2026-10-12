package com.legenkiy.dto;

import com.legenkiy.model.Auction;
import com.legenkiy.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionCreatedRs {
    private Auction auction;
    private Room room;
}
