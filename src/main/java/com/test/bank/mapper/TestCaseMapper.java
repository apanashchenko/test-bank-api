package com.test.bank.mapper;

import com.test.bank.model.TestCase;
import com.test.bank.payload.CreateTestCasePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {

    TestCase toTestCase(CreateTestCasePayload payload);
}
