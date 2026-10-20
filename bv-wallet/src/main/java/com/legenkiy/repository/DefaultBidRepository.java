package com.legenkiy.repository;

import com.legenkiy.model.BidEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DefaultBidRepository implements PanacheRepositoryBase<BidEntity, UUID> {
}
