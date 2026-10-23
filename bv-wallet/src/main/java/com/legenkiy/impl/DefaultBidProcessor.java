package com.legenkiy.impl;

import com.legenkiy.BidProcessor;
import com.legenkiy.BidService;
import com.legenkiy.ReservationService;
import com.legenkiy.WalletService;
import com.legenkiy.dto.CreateBalanceReservationRq;
import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.event.BidPlacedEvent;
import com.legenkiy.excpetion.BidException;
import com.legenkiy.model.Bid;
import com.legenkiy.model.Wallet;
import com.legenkiy.user.UserService;
import com.legenkiy.user.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DefaultBidProcessor implements BidProcessor {

    private final BidService bidService;
    private final Event<BidPlacedEvent> bidPlacedEvent;
    private final UserService userService;
    private final WalletService walletService;
    private final ReservationService reservationService;

    @Override
    @Transactional
    public void makeBid(MakeBidCommand command) {
        UUID walletId = validateUserAndGetWalletId(command.userId(), command.amount());
        reservationService.create(new CreateBalanceReservationRq(command.roomId(), walletId, command.amount()));
        Bid bid = bidService.create(command);
        bidPlacedEvent.fire(createNewBidEvent(bid));
    }

    private UUID validateUserAndGetWalletId(UUID userId, double bidAmount) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            String message = "Bid failed: user with id = %s not found".formatted(userId);
            log.debug(message);
            throw new BidException(message);
        }

        Wallet wallet = walletService.getByUserId(userId);
        double balance = wallet.getBalance();
        double reservedBalance = reservationService.getAmountByWalletId(wallet.getId());
        double availableBalance = balance - reservedBalance;

        if (availableBalance < bidAmount) {
            String message = ("Bid failed: available balance less then bid amount: available = %s, bid = %s, " +
                    "userId = %s, walletId = %s").formatted(availableBalance, bidAmount, userId, wallet.getId());
            log.debug(message);
            throw new BidException(message);
        }

        return wallet.getId();
    }

    private BidPlacedEvent createNewBidEvent(Bid bid) {
        return BidPlacedEvent.builder()
                .bidId(bid.getId())
                .roomId(bid.getRoomId())
                .userId(bid.getUserId())
                .amount(bid.getAmount())
                .createdAt(bid.getCreatedAt())
                .build();
    }
}
