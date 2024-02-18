package com.m2p.at.ftbtests.api.rest.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( monochrome = true,
        features = "src/test/resources/features/",
        dryRun = false,
        glue = {"com/m2p/at/ftbtests/api/rest/bdd/steps"})
public class FtbCucumberBasedTest {
    //TODO: Consider usage of Cucumber's Before/After etc.
}
