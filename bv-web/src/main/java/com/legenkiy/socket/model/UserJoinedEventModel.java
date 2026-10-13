package com.legenkiy.socket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserJoinedEventModel extends BaseEventModel {
    private UUID roomId;
    private UUID userId;
    private Instant joinedAt;
}
