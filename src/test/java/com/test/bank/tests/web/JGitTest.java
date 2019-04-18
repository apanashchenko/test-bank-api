package com.test.bank.tests.web;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JGitTest {

    @Test
    public void name() throws GitAPIException, IOException {

        final String dir = System.getProperty("user.dir") + "/test_bank_repo";

        File repo = new File(dir);

        System.out.println("Dir " + dir);

//        Git.cloneRepository()
//                .setURI("https://github.com/SergeyPirogov/test-bank.git")
//                .setDirectory(repo)
//                .call();

        PullResult call = Git.open(repo)
                .pull()
                .call();

        System.out.println(call.isSuccessful());

        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(repo + "/user.yml");
        Map<String, Object> obj = yaml.load(inputStream);
        System.out.println(obj);
    }
}
