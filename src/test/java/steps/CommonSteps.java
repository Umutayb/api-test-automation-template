package steps;

import api_assured.ApiUtilities;
import bookstore.Authorisation;
import bookstore.BookStore;
import context.ContextStore;
import gpt.utilities.DataGenerator;
import io.cucumber.java.*;
import common.Utilities;
import io.cucumber.java.en.Given;
import models.bookstore.CredentialModel;
import models.bookstore.TokenResponse;
import models.bookstore.UserResponse;

public class CommonSteps extends ApiUtilities {

    Authorisation auth = new Authorisation();


    @Before
    public void before(Scenario scenario){
        Utilities.scenario = scenario;
        if (scenario.getSourceTagNames().contains("@Authorise")){
            auth.createUserWithGpt();
            auth.generateTokenForContext();
        }

    }

    @After
    public void after(Scenario scenario){
        if (scenario.isFailed()){log.new Warning(scenario.getName() + ": FAILED!");}
        else log.new Success(scenario.getName() + ": PASS!");
    }

}
