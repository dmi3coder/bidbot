package de.optimax.bidbot.bdd.stepdefs;


import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BiddingFeaturesTest {
    @When("test runs")
    public void testRuns() {
        System.out.println("TEST Runs");
    }

    @Then("this case completes")
    public void thisCaseCompletes() {
        System.out.println("COMPLETED");
    }
}
