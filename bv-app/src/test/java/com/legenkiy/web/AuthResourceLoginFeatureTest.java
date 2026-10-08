package com.legenkiy.web;

import com.github.database.rider.core.api.dataset.DataSet;
import com.legenkiy.AbstractIntegrationTest;
import com.legenkiy.jwt.JwtService;
import com.legenkiy.jwt.model.AuthTokens;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import lombok.SneakyThrows;

import static io.restassured.RestAssured.given;

import static com.legenkiy.AbstractIntegrationTest.Fixtures.*;
import static com.legenkiy.web.AuthResourceLoginFeatureTest.Fixtures.*;
import static org.mockito.Mockito.when;

@QuarkusTest
public class AuthResourceLoginFeatureTest extends AbstractIntegrationTest {

    @InjectMock
    JwtService jwtService;

    @Test
    @SneakyThrows
    @DataSet("web/login/given/table_with_first_user.yml")
    void givenCorrectData_login_shouldReturnJwtTokens() {
        when(jwtService.issueTokens(FIRST_USER_USERNAME, FIRST_USER_ID)).thenReturn(buildAuthTokens());

        String response = given()
                .contentType(ContentType.JSON)
                .body(readFile(CORRECT_LOGIN_DATA_PATH))
                .post(BASE_API)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .cookie("refresh_token")
                .extract()
                .body()
                .asString();

        JSONAssert.assertEquals(buildTokenExpected(), response, JSONCompareMode.LENIENT);
    }

    @Test
    @DataSet("web/login/given/table_with_first_user.yml")
    void givenDataWithNonExistingUsername_login_shouldReturnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .body(readFile(INCORRECT_LOGIN_DATA_PATH))
                .post(BASE_API)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DataSet("web/login/given/table_with_first_user.yml")
    void givenDataWithIncorrectPassword_login_shouldReturnBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body(readFile(INCORRECT_PASSWORD_PATH))
                .post(BASE_API)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @SneakyThrows
    void givenMalformedData_login_shouldReturnBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body(readFile(MALFORMED_LOGIN_DATA_PATH))
                .post(BASE_API)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }


    static class Fixtures {
        public static String BASE_API = "api/auth/login";

        public static String CORRECT_LOGIN_DATA_PATH = "web/login/json/correct_login_data.json";
        public static String INCORRECT_LOGIN_DATA_PATH = "web/login/json/incorrect_login_data.json";
        public static String MALFORMED_LOGIN_DATA_PATH = "web/login/json/malformed_login_data.json";
        public static String INCORRECT_PASSWORD_PATH = "web/login/json/correct_username_with_wrong_password.json";

        public static AuthTokens buildAuthTokens() {
            return new AuthTokens("accessToken", "refreshToken");
        }

        public static String buildTokenExpected() {
            return """
                        {
                        "accessToken": "accessToken",
                        "refreshToken": "refreshToken"
                        }
                    """;
        }

    }
}
