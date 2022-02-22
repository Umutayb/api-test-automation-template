package petStore;

import models.pet.Pet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface PetStoreServices {

    String BASE_URL = PetStoreApi.BASE_URL;

    @Headers({"accept: application/json","Content-Type: application/json"})
    @POST(BASE_URL + PetStoreApi.PET_SUFFIX)
    Call<Pet> postPet(@Body Pet pet);

    @Headers({"accept: application/json","Content-Type: application/json"})
    @POST(BASE_URL + PetStoreApi.PET_SUFFIX + PetStoreApi.FIND_BY_STATUS_SUFFIX)
    Call<List<Pet>> findPetByStatus(@Path("status") String status);
}
