package steps;

import io.cucumber.java.en.*;
import petStore.PetStore;

public class PetStoreSteps {

    PetStore petStore = new PetStore();

    @Given("Post pet named {}, with status {}")
    public void search(String name,String status){petStore.addPet(name,status);}

    @Given("Find pet and verify by status {}")
    public void findPetByStatus(String status){petStore.findPetByStatus(status.toLowerCase());}

    @Given("Post a picture for pet with id {}, with picture url: {}")
    public void postPetPic(Long petId, String url){petStore.uploadPetPicture(petId,url);}

    @Given("Get pet with id: {}")
    public void getPetById(Long petId){petStore.getPetById(petId);}

    @Given("Delete pet with id: {}")
    public void deletePetById(Long petId){petStore.deletePetById(petId);}
}
