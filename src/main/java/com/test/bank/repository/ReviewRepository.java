package com.test.bank.repository;

import com.test.bank.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Iterable<Review> findAllByTestCaseId(Long id);
}
