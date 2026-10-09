package com.legenkiy.web;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.legenkiy.AbstractIntegrationTest;
import com.legenkiy.CommonGenerator;
import com.legenkiy.jwt.JwtService;
import com.legenkiy.jwt.model.AuthTokens;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_TOKEN_JTI;
import static com.legenkiy.AbstractIntegrationTest.Fixtures.SECOND_TOKEN_JTI;
import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_USER_USERNAME;
import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_USER_ID;

import static com.legenkiy.web.AuthResourceRefreshFeatureTest.Fixtures.BASE_REFRESH_API;
import static com.legenkiy.web.AuthResourceRefreshFeatureTest.Fixtures.CORRECT_LOGIN_DATA;
import static com.legenkiy.web.AuthResourceRefreshFeatureTest.Fixtures.BASE_LOGIN_API;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@QuarkusTest
public class AuthResourceRefreshFeatureTest extends AbstractIntegrationTest {

    @InjectMock
    CommonGenerator generator;
    @Inject
    JwtService jwtService;

    @Test
    @SneakyThrows
    @DataSet(value = {
            "web/refresh/given/table_with_first_user.yml",
            "web/refresh/given/empty_table_refreshToken.yml",
            "web/refresh/given/empty_table_revokedRefreshToken.yml"
    })
    @ExpectedDataSet(value = {
            "web/refresh/then/table_with_revoked_token.yaml",
            "web/refresh/then/table_with_secondRefreshToken.yaml"
    }, ignoreCols = "revoked_at")
    void givenCorrectDataWithCorrectToken_refresh_shouldGiveNewToken() {

        when(generator.now()).thenReturn(Instant.now());
        when(generator.uuid()).thenReturn(FIRST_TOKEN_JTI).thenReturn(SECOND_TOKEN_JTI);

        String refreshToken = processLoginAndReturnRefreshToken();

        given()
                .cookie("refresh_token", refreshToken)
                .contentType(ContentType.JSON)
                .post(BASE_REFRESH_API)
                .then()
                .statusCode(200);
    }

    @Test
    @SneakyThrows
    @DataSet(value = {
            "web/refresh/given/table_with_first_user.yml",
            "web/refresh/given/empty_table_refreshToken.yml",
            "web/refresh/given/empty_table_revokedRefreshToken.yml"
    })
    void givenExpiredToken_refresh_shouldReturnUnauthorized() {

        when(generator.now()).thenReturn(Instant.now().minus(Duration.of(10, ChronoUnit.DAYS)));
        when(generator.uuid()).thenReturn(FIRST_TOKEN_JTI);

        String refreshToken = processLoginAndReturnRefreshToken();

        given()
                .cookie("refresh_token", refreshToken)
                .contentType(ContentType.JSON)
                .post(BASE_REFRESH_API)
                .then()
                .statusCode(401);

    }

    @Test
    @SneakyThrows
    @DataSet(value = {
            "web/refresh/given/table_with_first_user.yml",
            "web/refresh/given/empty_table_refreshToken.yml",
            "web/refresh/given/table_with_revokedRefreshedToken.yaml"
    })
    void givenRevokedToken_refresh_shouldReturnUnauthorized() {
        when(generator.now()).thenReturn(Instant.now());
        when(generator.uuid()).thenReturn(FIRST_TOKEN_JTI);

        AuthTokens tokens = jwtService.issueTokens(FIRST_USER_USERNAME, FIRST_USER_ID);

        given()
                .cookie("refresh_token", tokens.refreshToken())
                .contentType(ContentType.JSON)
                .post(BASE_REFRESH_API)
                .then()
                .statusCode(401);
    }

    private String processLoginAndReturnRefreshToken() {
        return given()
                .contentType(ContentType.JSON)
                .body(readFile(CORRECT_LOGIN_DATA))
                .post(BASE_LOGIN_API)
                .then()
                .extract()
                .body()
                .path("refreshToken");
    }

    static class Fixtures {
        public static String BASE_REFRESH_API = "/api/auth/refresh";
        public static String BASE_LOGIN_API = "/api/auth/login";

        public static String CORRECT_LOGIN_DATA = "web/refresh/json/correct_login_data.json";
    }

}
