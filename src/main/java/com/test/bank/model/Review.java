package com.test.bank.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Review {

    private Long id;
    private TestCase testCase;

}
