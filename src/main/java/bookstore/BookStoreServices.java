package bookstore;

import models.bookstore.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static bookstore.BookStoreAPI.*;

public interface BookStoreServices {
    String BASE_URL = BookStoreAPI.BASE_URL;

    @GET(BOOKSTORE_VER_SUFFIX + BOOKS_SUFFIX)
    Call<BookListModel> getAllBooks();

    @POST(ACCOUNT_VER_SUFFIX + USER_SUFFIX)
    Call<UserResponse> postUser(@Body CredentialModel credentialModel);
    @POST(ACCOUNT_VER_SUFFIX + TOKEN_SUFFIX)
    Call<TokenResponse> generateToken(@Body CredentialModel credentialModel);

    interface Authorised {
        String BASE_URL = BookStoreAPI.BASE_URL;

        @POST(BOOKSTORE_VER_SUFFIX + BOOKS_SUFFIX)
        Call<PostBookResponse> postBooks(@Body CollectionOfIsbn collection);

        @GET(ACCOUNT_VER_SUFFIX + USER_SUFFIX + UUID)
        Call<UserResponse> getUser(@Path("UUID") String UserId);
    }
}
