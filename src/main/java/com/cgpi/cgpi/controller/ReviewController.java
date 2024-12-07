package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.entity.Review;
import com.cgpi.cgpi.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping
    public Review save(@RequestBody Review review) {
        return reviewService.save(review);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        reviewService.deleteById(id);
    }
}
