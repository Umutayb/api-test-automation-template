package steps;

import exceptions.FailedCallException;
import io.cucumber.java.en.Given;
import models.pet.Pet;
import org.junit.Assert;
import petStore.PetStore;
import utils.Printer;

public class PetStoreSteps {

    PetStore petStore = new PetStore();
    Printer log = new Printer(PetStoreSteps.class);

    @Given("Post pet named {}, with status {}")
    public void search(String name,String status){
        try {petStore.addPet(name,status);}
        catch (FailedCallException e) {
            log.new Error(e.getStackTrace());
            Assert.fail("Step failed: search -> PetStoreSteps");
        }
    }

    @Given("Find pet and verify by status {}")
    public void findPetByStatus(String status){
        try {
            for (Pet pet: petStore.findPetByStatus(status.toLowerCase())) {
                log.new Info(pet.getName() + ", status: " + pet.getStatus());
                Assert.assertEquals(status,pet.getStatus());
            }
        }
        catch (FailedCallException e) {
            log.new Error(e.getStackTrace());
            Assert.fail("Step failed: findPetByStatus -> PetStoreSteps");
        }
    }
}
