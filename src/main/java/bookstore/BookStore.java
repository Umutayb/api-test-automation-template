package bookstore;

import api_assured.ApiUtilities;
import api_assured.ServiceGenerator;
import context.ContextStore;
import models.bookstore.*;
import okhttp3.Headers;
import retrofit2.Call;

public class BookStore extends ApiUtilities {

    BookStoreServices bookStore = new ServiceGenerator().generate(BookStoreServices.class);

    BookStoreServices.Authorised bookStoreAuthorized = new ServiceGenerator(
            new Headers.Builder().add("Authorization", "Bearer " + ContextStore.get("token").toString()).build()
    ).generate(BookStoreServices.Authorised.class);


    public BookListModel getAllBooks() {
        log.new Info("Getting all the books from database");
        Call<BookListModel> bookListCall = bookStore.getAllBooks();
        return perform(bookListCall, true, true);
    }

    public PostBookResponse postBooks(CollectionOfIsbn collection) {
        log.new Info("Posting book(s)");
        Call<PostBookResponse> bookCall = bookStoreAuthorized.postBooks(collection);
        return perform(bookCall, true, true);
    }

    public UserResponse getUser(String userId) {
        log.new Info("Getting user: " + userId);
        Call<UserResponse> userCall = bookStoreAuthorized.getUser(userId);
        return perform(userCall, true, true);
    }

    public Object deleteUser(String userId) {
        log.new Info("Deleting user");
        Call<Object> userCall = bookStoreAuthorized.deleteUser(userId);
        return perform(userCall, true, true);
    }

}
