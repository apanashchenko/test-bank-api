package com.test.bank;

import io.swagger.client.ApiClient;
import io.swagger.client.api.FileControllerApi;
import io.swagger.client.api.ProjectControllerApi;
import io.swagger.client.api.PullRequestsControllerApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("http://localhost:8088");
        return apiClient;
    }

    @Bean
    public ProjectControllerApi controllerApi() {
        return apiClient().buildClient(ProjectControllerApi.class);
    }

    @Bean
    public FileControllerApi fileService() {
        return apiClient().buildClient(FileControllerApi.class);
    }

    @Bean
    public PullRequestsControllerApi pullRequestService() {
        return apiClient().buildClient(PullRequestsControllerApi.class);
    }
}
