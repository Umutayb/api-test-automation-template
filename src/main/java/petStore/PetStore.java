package petStore;

import models.commons.BaseResponse;
import models.pet.Pet;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.junit.Assert;
import retrofit2.Call;
import retrofit2.Response;
import utils.Caller;
import utils.Printer;
import utils.ServiceGenerator;
import java.io.File;
import java.net.URLConnection;
import java.util.List;

public class  PetStore extends Caller {

    PetStoreServices services = ServiceGenerator.generateService(PetStoreServices.class);
    Printer log = new Printer(PetStore.class);

    public void addPet(String name, String status) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setStatus(status);

        Call<Pet> postPet = services.postPet(pet);
        Pet responseModel = perform(postPet, true, true, "postPet -> PetStoreServices");
        log.new Important(responseModel.getName());
        log.new Important(responseModel.getStatus());
        log.new Success(responseModel.getId());
    }

    public void getPetById(Long petId){
        Call<Pet> getPet = services.getPetById(petId);
        Pet response = perform(getPet, true, false, "getPetById -> PetStoreServices");
        log.new Info("Pet name is: " + response.getName());
        Assert.assertEquals(petId,response.getId());
    }

    public void deletePetById(Long petId){
        Call<BaseResponse> getPet = services.deletePetById(petId);
        BaseResponse response = perform(getPet, true, false, "deletePetById -> PetStoreServices");
        log.new Info("The response message is: " + response.getMessage());
        Assert.assertEquals(200,response.getCode());
    }

    public void findPetByStatus(String status) {
        Call<List<Pet>> findPetByStatus = services.findPetByStatus(status);
        for (Pet pet: perform(findPetByStatus,true, false, "findPetByStatus -> PetStoreServices")) {
            log.new Info(pet.getName() + ", status: " + pet.getStatus());
            Assert.assertEquals(status,pet.getStatus());
        }
    }

    public void uploadPetPicture(Long petId, String pictureUrl){
        File file = new File(pictureUrl);
        RequestBody fileBody = RequestBody.create(file, MediaType.parse(URLConnection.guessContentTypeFromName(file.getName())));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(),fileBody);
        Call<BaseResponse> uploadPetPic = services.uploadPetImage(petId, part);
        Response<BaseResponse> response = getResponse(uploadPetPic,true, true, "uploadPetPicture -> PetStoreServices");
        log.new Info("The headers are: " + "\n" + response.headers());
        assert response.body() != null;
        log.new Info("The response message is: " + response.body().getMessage());
        Assert.assertEquals("application/json", response.headers().get("content-type"));
    }
}