package com.cgpi.cgpi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgpi.cgpi.entity.ShippingCondition;
import com.cgpi.cgpi.repository.ShippingConditionRepository;

@Service
public class ShippingService {

    @Autowired
    private ShippingConditionRepository shippingConditionRepository;

    public Double calculateShipping(String region, Double cartTotal) {
        List<ShippingCondition> conditions = shippingConditionRepository.findByRegionAndActive(region, true);

        for (ShippingCondition condition : conditions) {
            if (cartTotal >= condition.getMinCartTotal() && cartTotal <= condition.getMaxCartTotal()) {
                return condition.getShippingPrice();
            }
        }
        // Default shipping price if no conditions match
        return 0.0;
    }
}
