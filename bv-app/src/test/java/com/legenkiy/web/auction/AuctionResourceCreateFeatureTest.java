package com.legenkiy.web.auction;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.legenkiy.AbstractIntegrationTest;
import com.legenkiy.CommonGenerator;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.time.Instant;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@QuarkusTest
public class AuctionResourceCreateFeatureTest extends AbstractIntegrationTest {

    @InjectMock
    CommonGenerator commonGenerator;

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

        Header authorization = issueTokensAndGetHeader();
        when(commonGenerator.uuid()).thenReturn(Fixtures.FIRST_AUCTION_ID, Fixtures.FIRST_ROOM_ID);
        when(commonGenerator.now()).thenReturn(Fixtures.FIRST_AUCTION_CREATED_AT);

        String expectedRs = readFile(Fixtures.AUCTION_CREATION_RESPONSE);

        String response = given()
                .contentType(ContentType.JSON)
                .header(authorization)
                .body(readFile(Fixtures.CORRECT_AUCTION_CREATION_DATA))
                .post(Fixtures.BASE_API)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .body()
                .asString();

        JSONAssert.assertEquals(expectedRs, response, JSONCompareMode.LENIENT);
    }

    static class Fixtures {
        public static String BASE_API = "api/auction/create";

        public static String CORRECT_AUCTION_CREATION_DATA
                = "web/create-auction/json/correct_auction_creation_data.json";
        public static String AUCTION_CREATION_RESPONSE = "web/create-auction/json/auction_creation_response.json";

        public static UUID FIRST_AUCTION_ID = UUID.fromString("7f3b9c21-4d8a-4f6e-9c12-83a7e5d0b4f2");
        public static UUID FIRST_ROOM_ID = UUID.fromString("b61f873a-02df-4f87-a94e-9f2c6b8f1d29");

        public static Instant FIRST_AUCTION_CREATED_AT = Instant.parse("2026-10-14T12:00:00Z");


    }

}
