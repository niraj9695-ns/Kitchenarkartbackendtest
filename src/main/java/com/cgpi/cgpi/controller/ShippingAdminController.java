package com.cgpi.cgpi.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgpi.cgpi.entity.ShippingCondition;
import com.cgpi.cgpi.repository.ShippingConditionRepository;

@RestController
@RequestMapping("/api/admin/shipping")
public class ShippingAdminController {

    @Autowired
    private ShippingConditionRepository shippingConditionRepository;

    @PostMapping("/create")
    public ResponseEntity<ShippingCondition> createCondition(@RequestBody ShippingCondition condition) {
        condition.setCreatedAt(LocalDateTime.now());
        condition.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(shippingConditionRepository.save(condition));
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ShippingCondition> updateCondition(@PathVariable Long id, @RequestBody ShippingCondition condition) {
//        ShippingCondition existingCondition = shippingConditionRepository.findById(id).orElseThrow();
//        existingCondition.setMinCartTotal(condition.getMinCartTotal());
//        existingCondition.setMaxCartTotal(condition.getMaxCartTotal());
//        existingCondition.setShippingPrice(condition.getShippingPrice());
//        existingCondition.setActive(condition.getActive());
//        existingCondition.setUpdatedAt(LocalDateTime.now());
//        return ResponseEntity.ok(shippingConditionRepository.save(existingCondition));
//    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ShippingCondition> updateCondition(@PathVariable Long id, @RequestBody ShippingCondition condition) {
        ShippingCondition existingCondition = shippingConditionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Shipping Condition not found with id: " + id));
        
        existingCondition.setMinCartTotal(condition.getMinCartTotal());
        existingCondition.setMaxCartTotal(condition.getMaxCartTotal());
        existingCondition.setShippingPrice(condition.getShippingPrice());
        existingCondition.setActive(condition.getActive());
        existingCondition.setRegion(condition.getRegion()); // Ensure region is updated
        existingCondition.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.ok(shippingConditionRepository.save(existingCondition));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id) {
        shippingConditionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ShippingCondition>> listConditions() {
        return ResponseEntity.ok(shippingConditionRepository.findAll());
    }
   
    @GetMapping("/regions")
    public ResponseEntity<List<String>> getUniqueRegions() {
        List<String> uniqueRegions = shippingConditionRepository.findDistinctRegions();
        return ResponseEntity.ok(uniqueRegions);
    }
}
