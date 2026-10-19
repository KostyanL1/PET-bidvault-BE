package com.legenkiy.web.bid;

import com.legenkiy.BidProcessor;
import com.legenkiy.BidService;
import com.legenkiy.TestContainerResource;
import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.event.BidPlacedEvent;
import com.legenkiy.model.Bid;
import com.legenkiy.socket.WebSocketEventSender;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
class BidPlacedEventTransactionTest {

    @Inject
    BidProcessor bidProcessor;
    @InjectMock
    BidService bidService;
    @InjectMock
    WebSocketEventSender eventSender;

    @Test
    void sendsEventOnlyAfterCommit() {
        Bid bid = createBid();
        MakeBidCommand command = new MakeBidCommand(bid.getRoomId(), bid.getUserId(), bid.getAmount());
        when(bidService.create(command)).thenReturn(bid);

        QuarkusTransaction.requiringNew().run(() -> {
            bidProcessor.makeBid(command);
            verifyNoInteractions(eventSender);
        });

        verify(eventSender).sendBidEvent(new BidPlacedEvent(
                bid.getId(), bid.getRoomId(), bid.getUserId(), bid.getAmount(), bid.getCreatedAt()));
    }

    @Test
    void doesNotSendEventAfterRollback() {
        Bid bid = createBid();
        MakeBidCommand command = new MakeBidCommand(bid.getRoomId(), bid.getUserId(), bid.getAmount());
        when(bidService.create(command)).thenReturn(bid);

        assertThatThrownBy(() -> QuarkusTransaction.requiringNew().run(() -> {
            bidProcessor.makeBid(command);
            throw new IllegalStateException("Rollback test");
        })).isInstanceOf(RuntimeException.class);

        verifyNoInteractions(eventSender);
    }

    private Bid createBid() {
        return Bid.builder()
                .id(UUID.randomUUID())
                .roomId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .amount(100)
                .createdAt(Instant.parse("2026-10-19T12:00:00Z"))
                .build();
    }
}
