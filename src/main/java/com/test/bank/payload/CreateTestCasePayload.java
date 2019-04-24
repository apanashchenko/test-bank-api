package com.test.bank.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTestCasePayload {

    private String title;
    private String reference;
    private String labels;
    private String changedBy;

}
