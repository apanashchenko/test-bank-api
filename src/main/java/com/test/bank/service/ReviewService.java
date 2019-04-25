package com.test.bank.service;

import com.test.bank.model.Review;
import com.test.bank.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Iterable<Review> getAllReviewsForTestCase(Long id){
        return reviewRepository.findAllByTestCaseId(id);
    }

}
