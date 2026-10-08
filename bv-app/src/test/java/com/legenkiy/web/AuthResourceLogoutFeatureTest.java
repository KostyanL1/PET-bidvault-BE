package com.legenkiy.web;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.legenkiy.AbstractIntegrationTest;
import com.legenkiy.CommonGenerator;
import com.legenkiy.jwt.JwtService;
import com.legenkiy.jwt.model.AuthTokens;
import com.legenkiy.security.CookieService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.legenkiy.web.AuthResourceLogoutFeatureTest.Fixtures.*;
import static io.restassured.RestAssured.given;
import static com.legenkiy.AbstractIntegrationTest.Fixtures.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@QuarkusTest
public class AuthResourceLogoutFeatureTest extends AbstractIntegrationTest {

    @Inject
    JwtService jwtService;
    @InjectMock
    CommonGenerator generator;
    @InjectSpy
    CookieService cookieService;

    @Test
    @SneakyThrows
    @DataSet(value = {
            "web/logout/given/table_with_first_user.yml",
            "web/logout/given/empty_table_refreshToken.yml",
            "web/logout/given/empty_table_revokedRefreshToken.yml"
    })
    @ExpectedDataSet(value = {"web/logout/then/table_with_revoked_token.yaml"}, ignoreCols = "revoked_at")
    void givenCorrectTokenInCookie_logout_shouldRevokeTokenAndDeleteCookie() {

        Instant tokenIssuedAt = Instant.now();

        when(generator.uuid()).thenReturn(TOKEN_JTI);
        when(generator.now()).thenReturn(tokenIssuedAt);

        AuthTokens tokens = jwtService.issueTokens(FIRST_USER_USERNAME, FIRST_USER_ID);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokens.accessToken())
                .cookie("refresh_token", tokens.refreshToken())
                .post(BASE_LOGOUT_PATH)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        verify(cookieService).destroyCookies();

    }

    @Test
    void givenDataWithoutAuthorizationToken_logout_shouldReturnUnauthorized() {
        given()
                .contentType(ContentType.JSON)
                .post(BASE_LOGOUT_PATH)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    static class Fixtures {

        public static String BASE_LOGOUT_PATH = "/api/auth/logout";

    }

}
