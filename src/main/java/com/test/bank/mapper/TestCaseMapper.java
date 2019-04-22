package com.test.bank.mapper;

import com.test.bank.dto.GithubTestCaseDto;
import com.test.bank.dto.TestCaseDTO;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {

    TestCase toTestCase(TestCaseDTO testCaseDTO);

    @Mappings({
            @Mapping(target="title", source="testCaseDTO.title"),
//            TODO change field after add authorization
            @Mapping(target="userName", source="testCaseDTO.changedBy"),
            @Mapping(target="email", source="testCaseDTO.changedBy"),
            @Mapping(target="fileName", expression = "java(createFileName(testCaseDTO.getTitle()))"),
            @Mapping(target="branch", expression = "java(createFileName(testCaseDTO.getTitle()))"),
            @Mapping(target="repoName", source="project.repoName")
    })
    GithubTestCaseDto toGithubTestCaseDto(Project project, TestCaseDTO testCaseDTO);


    /**
     * Use just inside interface
     * */
    default String createFileName(String name) {
        return name.toLowerCase().replace(" ", "-");
    }
}
