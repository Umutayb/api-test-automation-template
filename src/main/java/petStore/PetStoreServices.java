package petStore;

import models.commons.BaseResponse;
import models.pet.Pet;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface PetStoreServices {

    String BASE_URL = PetStoreApi.BASE_URL;

    @Headers({"accept: application/json","Content-Type: application/json"})
    @POST(BASE_URL + PetStoreApi.PET_SUFFIX)
    Call<Pet> postPet(@Body Pet pet);

    @Headers({"accept: application/json","Content-Type: application/json"})
    @PUT(BASE_URL + PetStoreApi.PET_SUFFIX)
    Call<Pet> updatePet(@Body Pet pet);

    @Headers({"accept: application/json"})
    @GET(BASE_URL + PetStoreApi.PET_SUFFIX + "{petId}/")
    Call<Pet> getPetById(@Path("petId") Long petId);

    @Headers({"accept: application/json"})
    @DELETE(BASE_URL + PetStoreApi.PET_SUFFIX + "{petId}/")
    Call<BaseResponse> deletePetById(@Path("petId") Long petId);

    @Headers({"accept: application/json"})
    @GET(BASE_URL + PetStoreApi.PET_SUFFIX + PetStoreApi.FIND_BY_STATUS_SUFFIX)
    Call<List<Pet>> findPetByStatus(@Query("status") String status);

    @Multipart
    @POST(BASE_URL + PetStoreApi.PET_SUFFIX + "{petId}/" + PetStoreApi.UPLOAD_IMAGE)
    Call<BaseResponse> uploadPetImage(@Path(value = "petId") Long petId, @Part MultipartBody.Part file);
}