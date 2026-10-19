package com.legenkiy.rest.wallet.impl;

import com.legenkiy.MockedTopUpService;
import com.legenkiy.WalletService;
import com.legenkiy.dto.TopUpCommand;
import com.legenkiy.dto.TopUpRs;
import com.legenkiy.model.Wallet;
import com.legenkiy.rest.user.utils.ResourcesUtils;
import com.legenkiy.rest.wallet.WalletResource;
import com.legenkiy.rest.wallet.dto.BalanceTopUpRq;
import com.legenkiy.rest.wallet.dto.BalanceTopUpRs;
import com.legenkiy.rest.wallet.dto.WalletRs;
import com.legenkiy.security.rest.TokenFilter;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

import static com.legenkiy.common.BaseApi.WALLET_BASE_PATH;

@Slf4j
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Path(WALLET_BASE_PATH)
public class DefaultWalletResource implements WalletResource {

    private final MockedTopUpService mockedTopUpService;
    private final WalletService walletService;
    private final JsonWebToken jsonWebToken;

    @Override
    @POST
    @Path(TOP_UP_PATH)
    @TokenFilter
    @Authenticated
    public Response topUpBalance(BalanceTopUpRq command) {
        TopUpRs response = mockedTopUpService.topUpBalance(new TopUpCommand(command.userId(), command.amount()));

        return Response
                .status(Response.Status.OK)
                .entity(new BalanceTopUpRs(response.userId(), response.amount()))
                .build();
    }

    @Override
    @GET
    @Path(GET_WALLET_FOR_CURRENT_USER_PATH)
    @TokenFilter
    @Authenticated
    public Response getWalletForCurrentUser() {
        UUID userId = ResourcesUtils.extractPrincipalId(jsonWebToken);
        log.debug("Returns wallet by userId = {}", userId);
        Wallet wallet = walletService.getByUserId(userId);
        return Response
                .status(Response.Status.OK)
                .entity(new WalletRs(wallet.getId(), wallet.getUserId(), wallet.getBalance()))
                .build();
    }
}
