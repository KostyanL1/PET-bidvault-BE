package com.legenkiy.rest.auction.impl;

import com.legenkiy.AuctionService;
import com.legenkiy.rest.auction.AuctionResource;
import com.legenkiy.rest.auction.mapper.AuctionMapper;
import com.legenkiy.rest.auction.model.BidRq;
import com.legenkiy.rest.auction.model.CreateAuctionRq;
import com.legenkiy.dto.AuctionCreatedRs;
import com.legenkiy.dto.CreateAuctionCommand;
import com.legenkiy.security.rest.TokenFilter;
import com.legenkiy.rest.user.utils.ResourcesUtils;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

import static com.legenkiy.common.BaseApi.AUCTION_BASE_PATH;

@Slf4j
@Provider
@Path(AUCTION_BASE_PATH)
@RequiredArgsConstructor
public class DefaultAuctionResource implements AuctionResource {

    private final AuctionService auctionService;
    private final SecurityIdentity identity;
    private final JsonWebToken jsonWebToken;
    private final AuctionMapper auctionMapper;

    @Override
    @POST
    @Path(CREATE_PATH)
    @Authenticated
    @TokenFilter
    public Response create(CreateAuctionRq rq) {
        String username = ResourcesUtils.extractUsername(identity);
        log.debug("User {} creating auction with title={}, duration={}", username, rq.title(), rq.duration());

        UUID currentUserId = ResourcesUtils.extractPrincipalId(jsonWebToken);
        CreateAuctionCommand command = auctionMapper.toCreateCommand(rq, currentUserId);
        AuctionCreatedRs auctionCreatedRs = auctionService.create(command);

        log.debug("User {} created auction: auctionId={}", username, auctionCreatedRs.getAuction().getId());
        return Response
                .status(Response.Status.CREATED)
                .entity(auctionCreatedRs)
                .build();
    }

    @Override
    public Response makeBid(BidRq rq) {
        return null;
    }
}
