package com.cgpi.cgpi.services;


import com.cgpi.cgpi.entity.Review;
import com.cgpi.cgpi.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}

