package com.legenkiy;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.junit5.DBUnitExtension;
import com.legenkiy.jwt.JwtService;
import com.legenkiy.jwt.model.AuthTokens;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.restassured.http.Header;
import jakarta.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(DBUnitExtension.class)
@QuarkusTestResource(TestContainerResource.class)
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, alwaysCleanAfter = true)
public abstract class AbstractIntegrationTest {

    @Inject
    DataSource dataSource;
    @Inject
    JwtService service;
    @InjectMock
    CommonGenerator generator;

    ConnectionHolder connectionHolder = () -> dataSource.getConnection();

    public String readFile(String path){
        try {
            return Files.readString(
                    Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Header issueTokensAndGetHeader() {
        when(generator.now()).thenReturn(Instant.now());
        when(generator.uuid()).thenReturn(Fixtures.FIRST_TOKEN_JTI);
        AuthTokens tokens = service.issueTokens(Fixtures.FIRST_USER_USERNAME, Fixtures.FIRST_USER_ID);
        return new Header("Authorization", "Bearer "+ tokens.accessToken());
    }

    public static class Fixtures {

        public static UUID FIRST_USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

        public static String FIRST_USER_NAME = "Jhon";
        public static String FIRST_USER_SURNAME = "Wick";
        public static String FIRST_USER_USERNAME = "jhonwick@test.com";
        public static String FIRST_USER_EMAIL = "testemail@gmail.com";

        public static String FIRST_USER_PASSWORD = "test1234";
        public static String FIRST_USER_PASSWORD_HASH = "$2a$10$uBhcNlmoZYpYpZyOXH71Lu0X.QHF1hiFqiyn5plFKKgFTIoTbdV1S";


        public static String FIRST_USER_ROLE = "USER";
        public static Instant FIRST_USER_CREATED_AT = Instant.parse("2026-10-07T20:33:42+00:00");
        public static Instant FIRST_USER_UPDATED_AT = Instant.parse("2026-10-07T20:33:42+00:00");

        public static UUID FIRST_TOKEN_JTI = UUID.fromString("11111111-1111-1111-1111-111111111222");
        public static UUID SECOND_TOKEN_JTI = UUID.fromString("11111111-1111-1111-1111-111111111333");

    }

}
