package com.legenkiy.impl;

import com.legenkiy.RoomEntityMapper;
import com.legenkiy.RoomService;
import com.legenkiy.exception.NotFoundException;
import com.legenkiy.model.Room;
import com.legenkiy.model.RoomEntity;
import com.legenkiy.model.RoomStatus;
import com.legenkiy.repository.DefaultRoomRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApplicationScoped
public class DefaultRoomService implements RoomService {

    private final RoomEntityMapper mapper;
    private final DefaultRoomRepository repository;

    @Override
    @Transactional
    public Room create(UUID auctionId) {
        Room room = mapper.toCreate(auctionId);
        RoomEntity entity = mapper.toEntity(room);
        repository.persist(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public Room markRoomWithStatus(UUID roomId, RoomStatus status) {
        RoomEntity entity = mapper.toEntity(getById(roomId));
        mapper.markWithStatus(entity, status);
        return mapper.toDto(entity);
    }

    @Override
    public Room getById(UUID roomId) {
        return findById(roomId).orElseThrow(
                () -> new NotFoundException("Room not found: roomId=%s".formatted(roomId)));
    }

    @Override
    public Optional<Room> findById(UUID roomId) {
        return repository.findByIdOptional(roomId).map(mapper::toDto);
    }

    @Override
    @Transactional
    public void deleteById(UUID roomId) {
        Room room = getById(roomId);
        repository.delete(mapper.toEntity(room));
    }

}
