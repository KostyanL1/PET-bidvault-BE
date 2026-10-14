package com.legenkiy.web.auction;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.legenkiy.AbstractIntegrationTest;
import com.legenkiy.dto.AuctionCreatedRs;
import com.legenkiy.model.Auction;
import com.legenkiy.model.AuctionStatus;
import com.legenkiy.model.Room;
import com.legenkiy.model.RoomStatus;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.time.Instant;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AuctionResourceCreateFeatureTest extends AbstractIntegrationTest {

    @Test
    @SneakyThrows
    @DataSet(value = {
            "web/create-auction/given/empty_auction_table.yml",
            "web/create-auction/given/empty_room_table.yml",
            "web/create-auction/given/table_with_first_user.yml",
            "web/create-auction/given/empty_table_refreshToken.yml"
    })
    @ExpectedDataSet(value = {
            "web/create-auction/then/first_auction_table.yml",
            "web/create-auction/then/first_room_table.yml"
    })
    void givenCorrectDataWithAuth_create_ShouldCreateAuctionAndRoomForIt() {

        AuctionCreatedRs expectedRs = Fixtures.createAuctionCreatedRs();

        String response = given()
                .contentType(ContentType.JSON)
                .header(issueTokensAndGetHeader())
                .body(readFile(Fixtures.CORRECT_AUCTION_CREATION_DATA))
                .post(Fixtures.BASE_API)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        JSONAssert.assertEquals(String.valueOf(expectedRs), response, JSONCompareMode.LENIENT);
    }

    static class Fixtures {
        public static String BASE_API = "api/auction/create";

        public static String CORRECT_AUCTION_CREATION_DATA
                = "web/create-auction/json/correct_auction_creation_data.json";

        public static UUID FIRST_AUCTION_ID = UUID.fromString("7f3b9c21-4d8a-4f6e-9c12-83a7e5d0b4f2");
        public static UUID FIRST_ROOM_ID = UUID.fromString("b61f873a-02df-4f87-a94e-9f2c6b8f1d29");

        public static String FIRST_AUCTION_TITLE = "Old guitar";
        public static String FIRST_AUCTION_DESCRIPTION
                = "Vintage acoustic guitar with warm tone and worn wooden finish.";

        public static double FIRST_AUCTION_START_PRICE = 100;
        public static long FIRST_AUCTION_DURATION = 900;
        public static Instant FIRST_AUCTION_CREATED_AT = Instant.parse("2026-10-14T12:00:00Z");
        public static Instant FIRST_AUCTION_UPDATED_AT = Instant.parse("2026-10-14T12:00:00Z");
        public static UUID FIRST_AUCTION_OWNER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
        public static int FIST_AUCTION_COUNT_OF_PARTICIPANTS = 0;
        public static Instant FIRST_ROOM_CREATED_AT = Instant.parse("2026-10-14T12:00:00Z");
        public static Instant FIRST_ROOM_UPDATED_AT = Instant.parse("2026-10-14T12:00:00Z");

        public static AuctionCreatedRs createAuctionCreatedRs() {
            return AuctionCreatedRs.builder()
                    .auction(buildFirstAuction())
                    .room(buildFirstRoom())
                    .build();
        }

        public static Auction buildFirstAuction() {
            return Auction.builder()
                    .id(FIRST_AUCTION_ID)
                    .title(FIRST_AUCTION_TITLE)
                    .description(FIRST_AUCTION_DESCRIPTION)
                    .status(AuctionStatus.CREATED)
                    .startedAt(FIRST_AUCTION_CREATED_AT)
                    .updatedAt(FIRST_AUCTION_UPDATED_AT)
                    .startPrice(FIRST_AUCTION_START_PRICE)
                    .duration(FIRST_AUCTION_DURATION)
                    .ownerId(FIRST_AUCTION_OWNER_ID)
                    .countOfParticipants(FIST_AUCTION_COUNT_OF_PARTICIPANTS)
                    .build();
        }

        public static Room buildFirstRoom() {
            return Room.builder()
                    .id(FIRST_ROOM_ID)
                    .auctionId(FIRST_AUCTION_ID)
                    .status(RoomStatus.CREATED)
                    .createdAt(FIRST_ROOM_CREATED_AT)
                    .updatedAt(FIRST_ROOM_UPDATED_AT)
                    .build();
        }
    }

}
