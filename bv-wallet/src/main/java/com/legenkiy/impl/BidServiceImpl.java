package com.legenkiy.impl;

import com.legenkiy.BidService;
import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.mapper.BidEntityMapper;
import com.legenkiy.model.Bid;
import com.legenkiy.repository.DefaultBidRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

}
