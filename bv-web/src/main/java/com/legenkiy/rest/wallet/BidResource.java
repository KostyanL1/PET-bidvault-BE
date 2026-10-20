package com.legenkiy.rest.wallet;

import com.legenkiy.common.BaseApi;
import com.legenkiy.rest.wallet.dto.MakeBidRq;
import jakarta.ws.rs.core.Response;

public interface BidResource extends BaseApi {

    Response bid(MakeBidRq command);

}
