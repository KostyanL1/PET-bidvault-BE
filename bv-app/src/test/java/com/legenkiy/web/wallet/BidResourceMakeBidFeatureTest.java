package com.legenkiy.web.wallet;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.legenkiy.AbstractIntegrationTest;
import com.legenkiy.CommonGenerator;
import com.legenkiy.socket.ConnectionManager;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.BasicWebSocketConnector;
import io.quarkus.websockets.next.Connection;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Instant;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_USER_ID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@QuarkusTest
public class BidResourceMakeBidFeatureTest extends AbstractIntegrationTest {

    @InjectMock
    CommonGenerator commonGenerator;

    @Inject
    BasicWebSocketConnector connector;

    @InjectMock
    ConnectionManager connectionManager;

    @TestHTTPResource("/")
    URI baseUri;

    @Test
    @SneakyThrows
    @DataSet(value = {
            "web/bid/given/table_with_first_user.yml",
            "web/bid/given/first_auction_table.yml",
            "web/bid/given/first_room_table.yml",
            "web/bid/given/table_with_first_user_wallet.yml",
            "web/bid/given/empty_table_with_first_user_bids.yml",
            "web/bid/given/empty_table_with_first_user_reserved_balance.yml"
    })
    @ExpectedDataSet(value = {
            "web/bid/then/table_with_first_user_bids.yml",
            "web/bid/then/table_with_first_user_reserved_balance.yml"
    })
    void givenCorrectData_bid_shouldCreateBidAndReservedBalance() {
        Header token = issueTokensAndGetHeader();
        String rawToken = token.getValue().substring("Bearer ".length());

        BlockingDeque<String> events = new LinkedBlockingDeque<>();

        Connection connection = openConnection(events, rawToken);

        when(commonGenerator.uuid()).thenReturn(Fixtures.FIRST_RESERVATION_ID, Fixtures.FIRST_BID_ID);
        when(commonGenerator.now()).thenReturn(Fixtures.FIRST_BID_CREATED_AT);
        when(connectionManager.isConnectionExist(Fixtures.FIRST_ROOM_ID, FIRST_USER_ID)).thenReturn(true);

        try {
            given()
                    .header(token)
                    .contentType(ContentType.JSON)
                    .body(readFile(Fixtures.JSON_WITH_CORRECT_BID_DATA))
                    .post(Fixtures.BID_API_PATH)
                    .then()
                    .statusCode(HttpStatus.SC_OK);

            String joined = events.poll(5, TimeUnit.SECONDS);

            assertNotNull(joined);
            assertTrue(joined.contains("USER_JOINED"));
        } finally {
            connection.closeAndAwait();
        }

    }

    @Test
    @DataSet(value = {
            "web/bid/given/table_with_first_user.yml",
            "web/bid/given/first_auction_table.yml",
            "web/bid/given/first_room_table.yml",
            "web/bid/given/table_with_first_user_wallet.yml",
            "web/bid/given/empty_table_with_first_user_bids.yml",
            "web/bid/given/empty_table_with_first_user_reserved_balance.yml"
    })
    @ExpectedDataSet(value = {
            "web/bid/given/empty_table_with_first_user_bids.yml",
            "web/bid/given/empty_table_with_first_user_reserved_balance.yml",
            "web/bid/given/table_with_first_user_wallet.yml"
    })
    void givenNoAuthorization_bid_shouldNotCreateBidAndReservedBalance() {
        given()
                .contentType(ContentType.JSON)
                .body(readFile(Fixtures.JSON_WITH_CORRECT_BID_DATA))
                .post(Fixtures.BID_API_PATH)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @DataSet(value = {
            "web/bid/given/table_with_first_user.yml",
            "web/bid/given/first_auction_table.yml",
            "web/bid/given/first_room_table.yml",
            "web/bid/given/table_with_first_user_wallet.yml",
            "web/bid/given/empty_table_with_first_user_bids.yml",
            "web/bid/given/empty_table_with_first_user_reserved_balance.yml"
    })
    @ExpectedDataSet(value = {
            "web/bid/given/empty_table_with_first_user_bids.yml",
            "web/bid/given/empty_table_with_first_user_reserved_balance.yml",
            "web/bid/given/table_with_first_user_wallet.yml"
    })
    void givenBalanceLessThanBidAmount_bid_shouldNotCreateBidAndReservedBalance() {
        Header token = issueTokensAndGetHeader();
        when(connectionManager.isConnectionExist(Fixtures.FIRST_ROOM_ID, FIRST_USER_ID)).thenReturn(true);

        given()
                .header(token)
                .contentType(ContentType.JSON)
                .body(readFile(Fixtures.JSON_WITH_BID_EXCEEDING_BALANCE))
                .post(Fixtures.BID_API_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo(Fixtures.BID_EXCEEDING_BALANCE_ERROR));
    }

    @Test
    @DataSet(value = {
            "web/bid/given/table_with_first_user.yml",
            "web/bid/given/first_auction_table.yml",
            "web/bid/given/first_room_table.yml",
            "web/bid/given/table_with_first_user_wallet.yml",
            "web/bid/given/empty_table_with_first_user_bids.yml",
            "web/bid/then/table_with_first_user_reserved_balance.yml"
    })
    @ExpectedDataSet(value = {
            "web/bid/given/empty_table_with_first_user_bids.yml",
            "web/bid/then/table_with_first_user_reserved_balance.yml",
            "web/bid/given/table_with_first_user_wallet.yml"
    })
    void givenReservedBalanceAndBidExceedingAvailableBalance_bid_shouldNotCreateBidAndReservedBalance() {
        Header token = issueTokensAndGetHeader();
        when(connectionManager.isConnectionExist(Fixtures.FIRST_ROOM_ID, FIRST_USER_ID)).thenReturn(true);

        given()
                .header(token)
                .contentType(ContentType.JSON)
                .body(readFile(Fixtures.JSON_WITH_BID_EXCEEDING_AVAILABLE_BALANCE))
                .post(Fixtures.BID_API_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo(Fixtures.BID_EXCEEDING_AVAILABLE_BALANCE_ERROR));
    }

    private Connection openConnection(Queue<String> events, String accessToken) {
        return connector
                .baseUri(baseUri)
                .path("/auction/" + Fixtures.FIRST_ROOM_ID)
                .addHeader("Cookie", "access_token=" + accessToken)
                .onTextMessage((socket, message) -> events.add(message))
                .connectAndAwait();
    }

    static class Fixtures {
        public static final String BID_API_PATH = "/api/bid";

        public static final String JSON_WITH_CORRECT_BID_DATA = "web/bid/json/bid_creation_correct_data.json";
        public static final String JSON_WITH_BID_EXCEEDING_BALANCE = "web/bid/json/bid_exceeding_balance.json";
        public static final String JSON_WITH_BID_EXCEEDING_AVAILABLE_BALANCE
                = "web/bid/json/bid_exceeding_available_balance.json";

        public static final UUID FIRST_ROOM_ID = UUID.fromString("b61f873a-02df-4f87-a94e-9f2c6b8f1d29");
        public static final UUID FIRST_WALLET_ID = UUID.fromString("7e2f4a91-6c38-4db0-9a57-b15e83d2c640");
        public static final UUID FIRST_RESERVATION_ID = UUID.fromString("a439c650-8a2b-4d73-bc61-95d07e3218f4");
        public static final UUID FIRST_BID_ID = UUID.fromString("c8a62f95-713b-4da0-bec6-2f490e8d1357");

        public static final double FIRST_WALLET_BALANCE = 200.0;
        public static final double FIRST_BID_AMOUNT = 100.0;
        public static final double FIRST_RESERVATION_AMOUNT = FIRST_BID_AMOUNT;
        public static final double BID_EXCEEDING_BALANCE_AMOUNT = 250.0;
        public static final double BID_EXCEEDING_AVAILABLE_BALANCE_AMOUNT = 150.0;
        public static final String BID_EXCEEDING_BALANCE_ERROR =
                ("Bid failed: available balance less then bid amount: available = %s, bid = %s, "
                        + "userId = %s, walletId = %s").formatted(
                        FIRST_WALLET_BALANCE, BID_EXCEEDING_BALANCE_AMOUNT, FIRST_USER_ID, FIRST_WALLET_ID);
        public static final String BID_EXCEEDING_AVAILABLE_BALANCE_ERROR =
                ("Bid failed: available balance less then bid amount: available = %s, bid = %s, "
                        + "userId = %s, walletId = %s").formatted(
                        FIRST_WALLET_BALANCE - FIRST_RESERVATION_AMOUNT,
                        BID_EXCEEDING_AVAILABLE_BALANCE_AMOUNT, FIRST_USER_ID, FIRST_WALLET_ID);
        public static final Instant FIRST_BID_CREATED_AT = Instant.parse("2026-10-14T12:05:00Z");
    }
}
