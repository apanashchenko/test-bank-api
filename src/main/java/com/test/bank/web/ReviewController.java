package com.test.bank.web;

import com.test.bank.model.Review;
import com.test.bank.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/{id}")
    public Review getReviewById(@PathVariable Long id){
        return reviewService.findReviewById(id);
    }
}
