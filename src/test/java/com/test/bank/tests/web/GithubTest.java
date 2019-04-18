package com.test.bank.tests.web;

import com.jcabi.github.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;

public class GithubTest {

    @Test
    public void testCanCompareTwo() throws IOException {
        Github github = new RtGithub("49d07e3d9bf9de5d6bde3ccce6b24e9c55119f2a");
        Repo repo = github.repos().get(
                new Coordinates.Simple("SergeyPirogov/test-bank")
        );

        Iterable<RepoCommit> iterate = repo.commits().iterate(new HashMap<>());

        for (RepoCommit repoCommit : iterate) {
            System.out.println(repoCommit.sha());
        }

        String diff = repo.commits().diff("7886c0beaee85fa248f325b9a81421ddf6d74358","61b1a357a7c6519f3255c0091972ce82239aeeb6");

        Iterable<Branch> branches = repo.branches().iterate();

        for (Branch branch : branches) {
            System.out.println(branch.name() + " "+ branch.commit().sha());
        }

        Pull pull = repo.pulls().create("Demo", "demo", "master");



        System.out.println(diff);
    }
}
