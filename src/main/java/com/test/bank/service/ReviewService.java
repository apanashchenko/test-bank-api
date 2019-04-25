package com.test.bank.service;

import com.test.bank.model.Review;
import com.test.bank.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Iterable<Review> getAllReviewsForTestCase(Long id){
        return reviewRepository.findAllByTestCaseId(id);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).get();
    }
}
