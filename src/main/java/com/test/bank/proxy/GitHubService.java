package com.test.bank.proxy;

import com.test.bank.response.InitRepoResponse;
import com.test.bank.dto.ProjectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("githubService")
public interface GitHubService {

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    InitRepoResponse initProjectRepo(ProjectDTO projectDTO);

}
