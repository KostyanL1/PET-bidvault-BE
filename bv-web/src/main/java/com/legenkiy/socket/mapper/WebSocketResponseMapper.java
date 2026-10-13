package com.legenkiy.socket.mapper;

import com.legenkiy.CommonGenerator;
import com.legenkiy.socket.model.EventType;
import com.legenkiy.socket.model.UserJoinedEventModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class WebSocketResponseMapper {

    private final CommonGenerator generator;

    public UserJoinedEventModel toUserJoinedEvent(UUID roomId, UUID userId) {
        return UserJoinedEventModel.builder()
                .roomId(roomId)
                .userId(userId)
                .joinedAt(generator.now())
                .type(EventType.USER_JOINED)
                .build();
    }

}
