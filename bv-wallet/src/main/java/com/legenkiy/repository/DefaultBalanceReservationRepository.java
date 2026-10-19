package com.legenkiy.repository;

import com.legenkiy.model.BalanceReservationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.UUID;

public class DefaultBalanceReservationRepository implements PanacheRepositoryBase<BalanceReservationEntity, UUID> {
}
