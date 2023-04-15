package bookstore;

import api_assured.ApiUtilities;
import api_assured.ServiceGenerator;
import common.Utilities;
import context.ContextStore;
import gpt.utilities.DataGenerator;
import io.cucumber.java.en.Given;
import models.bookstore.CredentialModel;
import models.bookstore.TokenResponse;
import models.bookstore.UserResponse;
import retrofit2.Call;

public class Authorisation extends ApiUtilities {

    BookStoreServices bookStore = new ServiceGenerator().generate(BookStoreServices.class);
    DataGenerator generator = new DataGenerator(Utilities.gpt);

    public UserResponse createUser(CredentialModel credentialModel) {
        log.new Info("Creating a user");
        Call<UserResponse> userCall = bookStore.postUser(credentialModel);
        return perform(userCall, true, true);
    }

    public TokenResponse generateToken(CredentialModel user) {
        log.new Info("Generating a token for " + user.getUserName());
        Call<TokenResponse> tokenCall = bookStore.generateToken(user);
        return perform(tokenCall, true, true);
    }


    public void createUserWithGpt() {
        generator.setTemperature(1.0);
        CredentialModel user = generator.instantiate(CredentialModel.class, "password");
        user.setPassword("Alpha1234*");
        UserResponse userResponse = createUser(user);
        ContextStore.put("user", user);
        ContextStore.put("userId", userResponse.getUserID());
    }

    public void generateTokenForContext() {
        TokenResponse tokenResponse = generateToken(ContextStore.get("user"));
        if (tokenResponse.getStatus().equalsIgnoreCase("failed"))
            log.new Warning("Process failed");
        else log.new Success("Token generated successfully");
        ContextStore.put("token", tokenResponse.getToken());
    }

}
