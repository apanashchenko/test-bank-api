package com.test.bank.payload;

import io.swagger.client.model.Committer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTestCasePayload {

    private String title;
    private String reference;
    private String labels;
    private String changedBy;
    private Committer committer;

}
