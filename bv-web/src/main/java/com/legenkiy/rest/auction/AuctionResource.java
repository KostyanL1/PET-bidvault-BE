package com.legenkiy.rest.auction;

import com.legenkiy.rest.auction.model.BidRq;
import com.legenkiy.rest.auction.model.CreateAuctionRq;
import com.legenkiy.common.BaseApi;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

public interface AuctionResource extends BaseApi {

    String CREATE_PATH = "/create";

    Response create(@Valid CreateAuctionRq rq);

    Response makeBid(@Valid BidRq rq);

}
