package com.distributed_lovable.account_service.mapper;

import com.distributed_lovable.account_service.dto.subscription.PlanResponse;
import com.distributed_lovable.account_service.dto.subscription.SubscriptionResponse;
import com.distributed_lovable.account_service.entity.Plan;
import com.distributed_lovable.account_service.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);
}
