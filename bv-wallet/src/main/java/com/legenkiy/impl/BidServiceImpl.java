package com.legenkiy.impl;

import com.legenkiy.BidService;
import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.exception.NotFoundException;
import com.legenkiy.mapper.BidEntityMapper;
import com.legenkiy.model.Bid;
import com.legenkiy.model.BidStatus;
import com.legenkiy.repository.DefaultBidRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidEntityMapper bidEntityMapper;
    private final DefaultBidRepository bidRepository;

    @Override
    @Transactional
    public Bid create(MakeBidCommand command) {
        Bid bid = bidEntityMapper.toCreate(command);
        bidRepository.persist(bidEntityMapper.toEntity(bid));
        return bid;
    }

    @Override
    @Transactional
    public Bid markWithStatus(UUID bidId, BidStatus status) {
        Bid bid = getBidById(bidId);
        bidEntityMapper.setStatus(bid,status);
        return bid;
    }

    @Override
    public Optional<Bid> findBidById(UUID bidId) {
        return bidRepository.findByIdOptional(bidId).map(bidEntityMapper::toDto);
    }

    @Override
    public Bid getBidById(UUID bidId) {
        return findBidById(bidId).orElseThrow(
                () -> new NotFoundException("Bid with id = %s not found".formatted(bidId)));
    }

}
