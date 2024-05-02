package stepdefs;

import base_urls.ContactListBaseUrl;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pages.ContactListAddUserPage;
import pages.ContactListLogInPage;
import pojos.user.UserResponsePojo;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ObjectMapperUtilities;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utilities.Authentication.generateToken;
import static utilities.ObjectMapperUtilities.convertJsonToJava;

public class CreateUserStepDefinitions extends ContactListBaseUrl{
    ContactListLogInPage logInPage = new ContactListLogInPage();
    ContactListAddUserPage addUserPage =new ContactListAddUserPage();
    static public String firstName;
    static public String lastName;
    static public String email;
    static public String password;
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
        Faker faker = new Faker();
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        password = "Password.123";
        addUserPage.firstNameField.sendKeys(firstName);
        addUserPage.lastNameField.sendKeys(lastName);
        addUserPage.emailField.sendKeys(email);
        addUserPage.passwordField.sendKeys(password);
    }

    @And("user clicks on submit button")
    public void userClicksOnSubmitButton() {
        addUserPage.submit.click();

    }

    @And("user closes browser")
    public void userClosesBrowser() {
        Driver.closeDriver();
    }

    @Then("verify the user via API request")
    public void verifyTheUserViaAPIRequest() {
        spec.pathParams("first","users"
        ,"second","me");
        //Set Expected Data
        String expectedStr ="{\n" +
                "    \"_id\": \"6633507bcad2be0013434e2b\",\n" +
                "    \"firstName\": \""+firstName+"\",\n" +
                "    \"lastName\": \""+lastName+"\",\n" +
                "    \"email\": \""+email+"\",\n" +
                "    \"__v\": 2\n" +
                "}";
        UserResponsePojo expectedData = convertJsonToJava(expectedStr,UserResponsePojo.class);
        Response response = given(spec).when().get("{first}/{second}");
        response.prettyPrint();
        response
                .then()
                .statusCode(200);
        //Do Assertions
        UserResponsePojo actualData = convertJsonToJava(response.asString(), UserResponsePojo.class);
        assertEquals(actualData.getFirstName(),expectedData.getFirstName());
        assertEquals(actualData.getLastName(),expectedData.getLastName());
        assertEquals(actualData.getEmail(),expectedData.getEmail());


    }
}
