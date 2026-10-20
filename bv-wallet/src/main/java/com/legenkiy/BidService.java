package com.legenkiy;

import com.legenkiy.dto.MakeBidCommand;
import com.legenkiy.model.Bid;

public interface BidService {

    Bid create(MakeBidCommand command);

}
