package com.test.bank.tests.web;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
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

        File repo = new File(dir + "/.git");

        System.out.println("Dir " + dir);

        Git git = Git.open(repo);

//        Ref ref = git.checkout().setCreateBranch(true)
//                .setName("new-branch")
//                .call();

        File myFile = new File(git.getRepository().getDirectory().getParent(), "testfile");

        myFile.createNewFile();

        git.add().addFilepattern("testfile").call();

        git.commit().setMessage("test file added").call();

        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider("SergeyPirogov", "Semen4ik20#1234")).call();
    }
}
