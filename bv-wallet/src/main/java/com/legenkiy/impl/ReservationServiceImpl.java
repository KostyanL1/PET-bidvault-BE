package com.legenkiy.impl;

import com.legenkiy.ReservationService;
import com.legenkiy.RoomService;
import com.legenkiy.dto.CreateBalanceReservationRq;
import com.legenkiy.mapper.BalanceReservationEntityMapper;
import com.legenkiy.model.BalanceReservation;
import com.legenkiy.repository.DefaultBalanceReservationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final DefaultBalanceReservationRepository reservationRepository;
    private final BalanceReservationEntityMapper mapper;
    private final RoomService roomService;

    @Override
    @Transactional
    public BalanceReservation create(CreateBalanceReservationRq rq) {
        UUID auctionId = roomService.getAuctionIdByRoomId(rq.roomId());
        BalanceReservation reservation = mapper.toCreate(rq, auctionId);
        reservationRepository.persist(mapper.toEntity(reservation));
        return reservation;
    }

    @Override
    public double getAmountByWalletId(UUID walletId) {
        return reservationRepository.getAmountOfReservationByWalletId(walletId);
    }
}
