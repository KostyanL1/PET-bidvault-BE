package com.legenkiy.repository;

import com.legenkiy.model.BalanceReservationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DefaultBalanceReservationRepository implements PanacheRepositoryBase<BalanceReservationEntity, UUID> {

    private static final String AMOUNT_OF_RESERVATION_QUERY = """
            SELECT COALESCE(SUM(br.amount), 0.0)
            FROM BalanceReservationEntity br
            WHERE br.walletId = :walletId
            """;

    public double getAmountOfReservationByWalletId(UUID walletId) {
        return getEntityManager()
                .createQuery(AMOUNT_OF_RESERVATION_QUERY, Double.class)
                .setParameter("walletId", walletId)
                .getSingleResult();

    }

}
