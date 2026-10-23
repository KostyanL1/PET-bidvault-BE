package com.legenkiy.rest.wallet;

import com.legenkiy.common.BaseApi;
import com.legenkiy.rest.wallet.dto.BalanceTopUpRq;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

public interface WalletResource extends BaseApi {

    String TOP_UP_PATH = "/top-ups";
    String GET_WALLET_FOR_CURRENT_USER_PATH = "/me";

    Response topUpBalance(@Valid BalanceTopUpRq command);

    Response getWalletForCurrentUser();

}
