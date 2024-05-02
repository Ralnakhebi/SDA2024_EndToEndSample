package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ContactListLogInPage;
import utilities.ConfigReader;
import utilities.Driver;

public class CreateUserStepDefinitions {
    ContactListLogInPage logInPage = new ContactListLogInPage();
    @Given("user goes to {string}")
    public void userGoesTo(String url) {
        Driver.getDriver().get(url);
    }

    @When("user clicks on sign up button")
    public void userClicksOnSignUpButton() {
        logInPage.signupButton.click();
    }

    @And("user enters firstname, lastname, email, password")
    public void userEntersFirstnameLastnameEmailPassword() {
    }

    @And("user clicks on submit button")
    public void userClicksOnSubmitButton() {
    }

    @And("user closes browser")
    public void userClosesBrowser() {
    }

    @Then("verify the user via API request")
    public void verifyTheUserViaAPIRequest() {
    }
}
