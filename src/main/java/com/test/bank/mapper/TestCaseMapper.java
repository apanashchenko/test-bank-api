package com.test.bank.mapper;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.model.TestCase;
import com.test.bank.payload.CreateTestCasePayload;
import com.test.bank.payload.UpdateTestCasePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {

    TestCase toTestCase(CreateTestCasePayload payload);
    TestCaseDTO toTestCaseDTO(TestCase testCase);
    TestCaseDTO toTestCaseDTO(UpdateTestCasePayload payload);
}
