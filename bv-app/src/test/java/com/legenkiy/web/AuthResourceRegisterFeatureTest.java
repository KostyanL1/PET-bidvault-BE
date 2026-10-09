package com.legenkiy.web;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.legenkiy.AbstractIntegrationTest;
import com.legenkiy.CommonGenerator;
import com.legenkiy.user.security.PasswordHasher;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_USER_ID;
import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_USER_CREATED_AT;
import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_USER_PASSWORD;
import static com.legenkiy.AbstractIntegrationTest.Fixtures.FIRST_USER_PASSWORD_HASH;
import static com.legenkiy.web.AuthResourceRegisterFeatureTest.Fixtures.BASE_API_PATH;
import static com.legenkiy.web.AuthResourceRegisterFeatureTest.Fixtures.CORRECT_USER_DATA_PATH;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;


@QuarkusTest
public class AuthResourceRegisterFeatureTest extends AbstractIntegrationTest {

    @InjectMock
    CommonGenerator commonGenerator;
    @InjectMock
    PasswordHasher passwordHasher;

    @Test
    @DataSet("web/register/given/empty_user_table.yml")
    @ExpectedDataSet("web/register/then/users_table_with_first_user.yml")
    void givenCorrectData_register_shouldCreateUser() {
        mockRegisterDependencies();

        given()
                .contentType(ContentType.JSON)
                .body(readFile(CORRECT_USER_DATA_PATH))
                .post(BASE_API_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DataSet("web/register/given/table_with_first_user.yml")
    @ExpectedDataSet("web/register/then/users_table_with_first_user.yml")
    void givenAlreadyRegisteredUser_register_shouldThrowException() {
        mockRegisterDependencies();

        given()
                .contentType(ContentType.JSON)
                .body(readFile(CORRECT_USER_DATA_PATH))
                .post(BASE_API_PATH)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    @DataSet("web/register/given/empty_user_table.yml")
    @ExpectedDataSet("web/register/then/empty_user_table.yml")
    void givenIncorrectData_register_shouldThrowException() {
        mockRegisterDependencies();

        given()
                .contentType(ContentType.JSON)
                .body(readFile(Fixtures.INCORRECT_USER_DATA_PATH))
                .post(BASE_API_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    private void mockRegisterDependencies() {
        when(commonGenerator.uuid()).thenReturn(FIRST_USER_ID);
        when(commonGenerator.now()).thenReturn(FIRST_USER_CREATED_AT);
        when(passwordHasher.hash(FIRST_USER_PASSWORD)).thenReturn(FIRST_USER_PASSWORD_HASH);
    }

    static class Fixtures {
        static String BASE_API_PATH = "api/auth/registration";

        static String CORRECT_USER_DATA_PATH = "web/register/json/correct_user_data_for_registration.json";
        static String INCORRECT_USER_DATA_PATH = "web/register/json/incorrect_user_data_for_registration.json";
    }

}
