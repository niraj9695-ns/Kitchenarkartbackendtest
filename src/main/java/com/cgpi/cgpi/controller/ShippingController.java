package com.cgpi.cgpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgpi.cgpi.services.ShippingService;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping("/calculate")
    public ResponseEntity<Double> calculateShipping(@RequestParam String region, @RequestParam Double cartTotal) {
        Double shippingPrice = shippingService.calculateShipping(region, cartTotal);
        return ResponseEntity.ok(shippingPrice);
    }
}
