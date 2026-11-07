# BidVault

An auction backend built as a modular monolith. 
Supports JWT authentication, auction rooms, wallet balances, 
bid reservations, and real-time WebSocket events. Auction settlement is still in development.

## Stack
```
· Java 21 
· Quarkus 3.40.1
· Gradle
· PostgreSQL 17
· Hibernate ORM with Panache
· Flyway
· JWT
· CDI events
· WebSockets Next
· Lombok
```

Integration tests use JUnit, REST Assured, Mockito, Testcontainers, and Database Rider. GitHub Actions runs Checkstyle, tests, and builds.

## Structure

```text
bv-app      Application configuration and integration tests
bv-web      REST endpoints, WebSockets, and request security
bv-user     Users, authentication, and token management
bv-auction  Auctions and their lifecycle operations
bv-room     Auction rooms
bv-wallet   Wallets, bids, and balance reservations
bv-common   Shared utilities and exceptions
bruno       API request collection
```

## Development

Requires JDK 21 and Docker. Configure `BV_POSTGRES_DB`, `BV_POSTGRES_USERNAME`, `BV_POSTGRES_PASSWORD`, `BV_POSTGRES_URL`, `JWT_ACCESS_EXPIRED`, and `JWT_REFRESH_EXPIRED` in your environment. Docker Compose reads database variables from `.env`; the application needs its variables exported separately.

Place RSA JWT keys at `bv-app/src/main/resources/jwt/privateKey.pem` (PKCS#8) and `publicKey.pem` (PEM). PostgreSQL is exposed on port `1717`; the API runs on `8080`.

```sh
docker compose up -d
./gradlew :bv-app:quarkusDev
```

## Checks

```sh
./gradlew checkstyleMain checkstyleTest
./gradlew :bv-app:test
./gradlew :bv-app:build -x :bv-app:test
```

On Windows, use `gradlew.bat` instead of `./gradlew`.
