package com.legenkiy.rest.wallet.impl;

import com.legenkiy.BidProcessor;
import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.rest.user.utils.ResourcesUtils;
import com.legenkiy.rest.wallet.BidResource;
import com.legenkiy.rest.wallet.dto.MakeBidRq;
import com.legenkiy.security.rest.TokenFilter;
import com.legenkiy.socket.ConnectionManager;
import io.quarkus.security.Authenticated;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
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

import static com.legenkiy.common.BaseApi.BID_BASE_PATH;

@Slf4j
@Provider
@Path(BID_BASE_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class DefaultBidResource implements BidResource {

    private final BidProcessor bidProcessor;
    private final JsonWebToken jsonWebToken;
    private final ConnectionManager connectionManager;

    @Override
    @POST
    @Authenticated
    @TokenFilter
    public Response bid(MakeBidRq command) {
        UUID userId = ResourcesUtils.extractPrincipalId(jsonWebToken);
        validate(command, userId);
        log.info("Requested bid for userId = {}, roomId = {}", userId, command.roomId());
        bidProcessor.makeBid(new MakeBidCommand(command.roomId(), userId, command.amount()));
        return Response.ok().build();
    }

    private void validate(MakeBidRq command, UUID userId) {
        if (!connectionManager.isConnectionExist(command.roomId(), userId)) {
            throw new ValidationException("Connection with user id = %s and room id = %s doesn't exist"
                    .formatted(userId, command.roomId()));
        }
    }
}
