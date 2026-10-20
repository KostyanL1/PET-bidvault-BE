package com.legenkiy;

import com.legenkiy.dto.MakeBidCommand;

public interface BidProcessor {

    void makeBid(MakeBidCommand command);

}
