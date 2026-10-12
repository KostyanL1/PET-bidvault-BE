package com.legenkiy.auction.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateAuctionRq(
        @NotBlank(message = "Title can't be blank")
        @NotNull(message = "Title must be present")
        @Length(min = 10, max = 50, message = "Title length must be from 10 to 50 chars")
        String title,
        @NotBlank(message = "Description can't be blank")
        @NotNull(message = "Description must be present")
        @Length(min = 30, max = 1000, message = "Description length must be from 50 to 1000 chars")
        String description,
        @Min(value = 1, message = "Start price for lot can't be less then 1$")
        double startPrice,
        @Min(value = 600, message = "Auction duration can't be less that 10 minutes")
        long duration
) {
}
