package com.legenkiy;

import com.legenkiy.dto.TopUpCommand;
import com.legenkiy.dto.TopUpRs;

public interface MockedTopUpService {

    TopUpRs topUpBalance(TopUpCommand command);

}
