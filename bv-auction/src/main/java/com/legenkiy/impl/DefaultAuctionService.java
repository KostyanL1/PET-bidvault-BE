package com.legenkiy.impl;

import com.legenkiy.AuctionEntityMapper;
import com.legenkiy.AuctionService;
import com.legenkiy.RoomService;
import com.legenkiy.dto.AuctionCreatedRs;
import com.legenkiy.dto.CreateAuctionCommand;
import com.legenkiy.dto.UpdateAuctionCommand;
import com.legenkiy.exception.NotFoundException;
import com.legenkiy.model.Auction;
import com.legenkiy.model.AuctionEntity;
import com.legenkiy.model.AuctionStatus;
import com.legenkiy.model.Room;
import com.legenkiy.repository.DefaultAuctionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class DefaultAuctionService implements AuctionService {

    private final AuctionEntityMapper mapper;
    private final DefaultAuctionRepository repository;
    private final RoomService roomService;

    @Override
    @Transactional
    public AuctionCreatedRs create(CreateAuctionCommand command) {
        Auction auction = mapper.toCreate(command);
        repository.persist(mapper.toEntity(auction));
        Room createRoom = roomService.create(auction.getId());
        return new AuctionCreatedRs(auction, createRoom);
    }

    @Override
    @Transactional
    public Auction markAuctionWithStatus(UUID auctionId, AuctionStatus status) {
        AuctionEntity entity = repository.findByIdOptional(auctionId).orElseThrow(()
                -> new NotFoundException("Auction not found: auctionId=%s".formatted(auctionId)));
        mapper.markWithStatus(entity, status);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public Auction update(UpdateAuctionCommand command) {
        Auction auction = getById(command.id());
        mapper.updateAuction(auction, command);
        repository.persist(mapper.toEntity(auction));
        return auction;
    }

    @Override
    @Transactional
    public int increaseCountOfParticipants(UUID auctionId) {
        Auction auction = getById(auctionId);
        int currentParticipants = auction.getCountOfParticipants();
        currentParticipants++;
        auction.setCountOfParticipants(currentParticipants);
        repository.persist(mapper.toEntity(auction));
        return currentParticipants;
    }

    @Override
    public Auction getById(UUID auctionId) {
        return findById(auctionId).orElseThrow(()
                -> new NotFoundException("Auction not found: auctionId=%s".formatted(auctionId)));
    }

    @Override
    public Optional<Auction> findById(UUID auctionId) {
        return repository.findByIdOptional(auctionId).map(mapper::toDto);
    }

    @Override
    @Transactional
    public void deleteById(UUID auctionId) {
        Auction auction = getById(auctionId);
        repository.delete(mapper.toEntity(auction));
    }

}
