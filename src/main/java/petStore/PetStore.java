package petStore;

import models.commons.BaseResponse;
import models.pet.Pet;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.junit.Assert;
import retrofit2.Call;
import utils.Caller;
import utils.Printer;
import utils.ServiceGenerator;
import java.io.File;
import java.util.List;

public class  PetStore extends Caller {

    PetStoreServices services = ServiceGenerator.generateService(PetStoreServices.class);
    Printer log = new Printer(PetStore.class);

    Pet thePet = new Pet();

    public void addPet(String name, String status) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setStatus(status);

        Call<Pet> postPet = services.postPet(pet);
        Pet responseModel = perform(postPet, true, "postPet -> PetStoreServices");
        log.new Important(responseModel.getName());
        log.new Important(responseModel.getStatus());
        log.new Success(responseModel.getId());
    }

    public void getPetById(Long petId){
        Call<Pet> getPet = services.getPetById(petId);
        Pet response = perform(getPet, true, "getPetById -> PetStoreServices");
        log.new Info("Pet name is: " + response.getName());
        Assert.assertEquals(petId,response.getId());
    }

    public void deletePetById(Long petId){
        Call<BaseResponse> getPet = services.deletePetById(petId);
        BaseResponse response = perform(getPet, true, "deletePetById -> PetStoreServices");
        response.printMessage();
        Assert.assertEquals(200,response.getCode());
    }

    public void findPetByStatus(String status) {
        Call<List<Pet>> findPetByStatus = services.findPetByStatus(status);
        for (Pet pet: perform(findPetByStatus,true,"findPetByStatus -> PetStoreServices")) {
            log.new Info(pet.getName() + ", status: " + pet.getStatus());
            Assert.assertEquals(status,pet.getStatus());
        }
    }

    public void uploadPetPicture(Long petId, String pictureUrl){
        File file = new File(pictureUrl);
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/png"));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(),fileBody);
        Call<BaseResponse> uploadPetPic = services.uploadPetImage(petId, part);
        BaseResponse response = perform(uploadPetPic,true,"uploadPetPicture -> PetStoreServices");
        response.printMessage();
        Assert.assertEquals(200,response.getCode());
    }
}