package com.distributed_lovable.account_service.service.impl;

import com.distributed_lovable.account_service.dto.subscription.SubscriptionResponse;
import com.distributed_lovable.account_service.entity.Plan;
import com.distributed_lovable.account_service.entity.Subscription;
import com.distributed_lovable.account_service.entity.User;
import com.distributed_lovable.account_service.mapper.SubscriptionMapper;
import com.distributed_lovable.account_service.repository.PlanRepository;
import com.distributed_lovable.account_service.repository.SubscriptionRepository;
import com.distributed_lovable.account_service.repository.UserRepository;
import com.distributed_lovable.account_service.service.SubscriptionService;
import com.distributed_lovable.common_lib.dto.PlanDto;
import com.distributed_lovable.common_lib.enums.SubscriptionStatus;
import com.distributed_lovable.common_lib.error.ResourceNotFoundException;
import com.distributed_lovable.common_lib.security.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final AuthUtil authUtil;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    private final Integer FREE_TIER_PROJECTS_ALLOWED = 100;

    @Override
    public SubscriptionResponse getCurrentSubscription() {
        Long userId = authUtil.getCurrentUserId();

        var currentSubscription = subscriptionRepository.findByUserIdAndStatusIn(userId, Set.of(
                SubscriptionStatus.ACTIVE, SubscriptionStatus.PAST_DUE, SubscriptionStatus.TRIALING
        )).orElse( new Subscription());
        return subscriptionMapper.toSubscriptionResponse(currentSubscription);
    }

    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {
        boolean exists = subscriptionRepository.existsByStripeSubscriptionId(subscriptionId);
        if(exists) return;

        User user = getUser(userId);
        Plan plan = getPlan(planId);

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subscriptionId)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();

        subscriptionRepository.save(subscription);

    }

    @Override
    @Transactional
    public void updateSubscription(String gatewaySubscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);
        boolean subscriptionHasBeenUpdated = false;
        if(status != null && status != subscription.getStatus()){
            subscription.setStatus(status);
            subscriptionHasBeenUpdated = true;
        }

        if(periodStart != null && !periodStart.equals(subscription.getCurrentPeriodStart())){
            subscription.setCurrentPeriodStart(periodStart);
            subscriptionHasBeenUpdated = true;
        }

        if(periodEnd != null && !periodEnd.equals(subscription.getCurrentPeriodEnd())){
            subscription.setCurrentPeriodEnd(periodEnd);
            subscriptionHasBeenUpdated = true;
        }

        if(cancelAtPeriodEnd != null && cancelAtPeriodEnd != subscription.getCancelAtPeriodEnd()){
            subscription.setCancelAtPeriodEnd(cancelAtPeriodEnd);
            subscriptionHasBeenUpdated = true;
        }

        if(planId != null && !planId.equals(subscription.getPlan().getId())){
            Plan newPlan = getPlan(planId);
            subscription.setPlan(newPlan);
            subscriptionHasBeenUpdated = true;
        }

        if(subscriptionHasBeenUpdated){
            log.debug("Subscription has been updated: {}", gatewaySubscriptionId);
            subscriptionRepository.save(subscription); // not needed since we used @Transactional but in enterprise apps they do this, to be safe.
        }
    }

    @Override
    public void cancelSubscription(String gatewaySubscriptionId) {
        Subscription subscription = getSubscription(gatewaySubscriptionId);

        subscription.setStatus(SubscriptionStatus.CANCELED);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void renewSubscriptionPeriod(String gateWaySubscriptionId, Instant periodStart, Instant periodEnd) {
        Subscription subscription = getSubscription(gateWaySubscriptionId);

        Instant newStart = periodStart != null ? periodStart : subscription.getCurrentPeriodEnd();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE || subscription.getStatus() == SubscriptionStatus.INCOMPLETE){
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        subscriptionRepository.save(subscription);
    }

    @Override
    public void markSubscriptionPastDue(String gateWaySubscriptionId) {
        Subscription subscription = getSubscription(gateWaySubscriptionId);

        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE){
            log.debug("Subscription is already past due, gateWaySubscriptionId: {}", gateWaySubscriptionId);
            return;
        }
        subscription.setStatus(SubscriptionStatus.PAST_DUE);
        subscriptionRepository.save(subscription);

        // Notify user about payment fail through email or sms
    }

    @Override
    public PlanDto getCurrentSubscribedPlanByUser() {
        SubscriptionResponse subscriptionResponse = getCurrentSubscription();
        return subscriptionResponse.plan();
    }

    // Utility methods

    private User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId){
        return planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", planId.toString()));
    }

    private Subscription getSubscription(String gateWaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(gateWaySubscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", gateWaySubscriptionId));
    }
}
