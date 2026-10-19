package com.legenkiy.socket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class BidSettleEventModel extends BaseEventModel{
    private UUID roomId;
    private UUID userId;
    private double amount;
}
