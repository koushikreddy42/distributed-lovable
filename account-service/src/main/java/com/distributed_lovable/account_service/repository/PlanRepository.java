package com.distributed_lovable.account_service.repository;

import com.distributed_lovable.account_service.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByStripePriceId(String stripePriceId);
}
