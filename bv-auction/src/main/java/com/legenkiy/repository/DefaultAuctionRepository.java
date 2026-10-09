package com.legenkiy.repository;

import com.legenkiy.model.AuctionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DefaultAuctionRepository implements PanacheRepositoryBase<AuctionEntity, UUID> {

}
