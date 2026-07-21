package com.distributed_lovable.account_service.dto.subscription;

import java.time.Instant;

public record SubscriptionResponse(
        PlanResponse plan,
        String status,
        Instant currentPeriodEnd,
        Long tokenUsedThisCycle
) {
}
