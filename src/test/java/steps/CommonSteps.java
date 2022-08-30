package steps;

import io.cucumber.java.*;
import common.Utilities;
import utils.Printer;

public class CommonSteps{

    Printer log = new Printer(CommonSteps.class);

    @Before
    public void before(Scenario scenario){
        Utilities.scenario = scenario;
    }

    @After
    public void after(Scenario scenario){
        if (scenario.isFailed()){log.new Warning(scenario.getName() + ": FAILED!");}
        else log.new Success(scenario.getName() + ": PASS!");
    }
}
