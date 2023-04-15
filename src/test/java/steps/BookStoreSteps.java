package steps;

import bookstore.Authorisation;
import bookstore.BookStore;
import common.Utilities;
import context.ContextStore;
import gpt.utilities.DataGenerator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import models.bookstore.*;
import org.junit.Assert;
import utils.StringUtilities;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.StringUtilities.Color.BLUE;

public class BookStoreSteps {

    BookStore bookStore = new BookStore();
    StringUtilities strUtils = new StringUtilities();
    Authorisation authorisation = new Authorisation();
    DataGenerator generator = new DataGenerator(Utilities.gpt);

    @Given("Get all books from database")
    public void getAllBooks() {
        BookListModel books = bookStore.getAllBooks();
        ContextStore.put("books", books);
    }

    @Given("Create user with following:")
    public void createUser(DataTable table) {
        Map<String, String> userMap = table.asMap();
        CredentialModel user = new CredentialModel();
        user.setUserName(userMap.get("Username"));
        user.setPassword(userMap.get("Password"));
        UserResponse userResponse = authorisation.createUser(user);
        ContextStore.put("user", user);
        ContextStore.put("userId", userResponse.getUserID());
        bookStore.log.new Success("User created successfully");
    }

    @Given("Create a randomized user")
    public void createUserWithGpt() {
        CredentialModel user = generator.instantiate(CredentialModel.class);
        UserResponse userResponse = authorisation.createUser(user);
        ContextStore.put("user", user);
        ContextStore.put("userId", userResponse.getUserID());
        bookStore.log.new Success("User created successfully");
    }

    @Given("Generate token for following user:")
    public void createUserGenerateToken(DataTable table) {
        Map<String, String> userMap = table.asMap();
        CredentialModel user = new CredentialModel();
        user.setUserName(userMap.get("Username"));
        user.setPassword(userMap.get("Password"));

        TokenResponse tokenResponse = authorisation.generateToken(user);
        if (tokenResponse.getStatus().equalsIgnoreCase("failed"))
            bookStore.log.new Warning("Process failed");
        else bookStore.log.new Success("Token generated successfully");
    }

    @Given("Generate token for the user in context")
    public void generateTokenForContext() {
        TokenResponse tokenResponse = authorisation.generateToken(ContextStore.get("user"));
        if (tokenResponse.getStatus().equalsIgnoreCase("failed"))
            bookStore.log.new Warning("Process failed");
        else bookStore.log.new Success("Token generated successfully");
    }

    @Given("Delete user by Id: {}")
    public void deleteUserById(String userId) {
        bookStore.deleteUser(userId);
        bookStore.log.new Success("User deleted successfully");
    }

    @Given("Delete the user in context")
    public void deleteUserInContext() {
        deleteUserById(ContextStore.get("userId"));
    }

    @Given("Post books by publisher named {} to the user in context")
    public void postBooks(String publisher) {
        List<CollectionOfIsbn.IsbnModel> isbns = new ArrayList<>();
        String userId = ContextStore.get("userId");

        for (BookModel book: bookStore.getAllBooks().getBooks())
            if (book.getPublisher().equalsIgnoreCase(publisher))
                isbns.add(new CollectionOfIsbn.IsbnModel(book.getIsbn()));
        CollectionOfIsbn collection = new CollectionOfIsbn(userId, isbns);

        PostBookResponse bookResponse = bookStore.postBooks(collection);
        ContextStore.put("postedBooks", bookResponse);
        bookStore.log.new Success("Books posted successfully");
    }

    @Given("Get user by id: {}")
    public void getUser(String userId) {
        UserResponse userResponse = bookStore.getUser(userId);
        ContextStore.put("userResponse", userResponse);
    }

    @Given("Get user in context")
    public void getUserInContext() {
        getUser(ContextStore.get("userId").toString());
    }

    @Given("Verify isbn numbers for posted books")
    public void verifyIsbns() {
        PostBookResponse postedBooks = ContextStore.get("postedBooks");
        UserResponse userResponse = ContextStore.get("userResponse");
        List<String> postedIsbn = new ArrayList<>();
        List<String> userBooks = new ArrayList<>();
        for (CollectionOfIsbn.IsbnModel isbn : postedBooks.getBooks())
            postedIsbn.add(isbn.getIsbn());
        for (BookModel book : userResponse.getBooks())
            userBooks.add(book.getIsbn());
        Assert.assertEquals("not match",postedIsbn,userBooks);
        bookStore.log.new Success("Isbns match");
    }

    @Given("Verify book information for the user in context")
    public void verifyBooks() {
        BookListModel books = ContextStore.get("books");
        UserResponse userResponse = ContextStore.get("userResponse");
        for (BookModel book : books.getBooks()) for (BookModel userBook : userResponse.getBooks())
            if (book.getIsbn().equalsIgnoreCase(userBook.getIsbn())){
                Assert.assertEquals("Isbn not match",userBook.getIsbn(),book.getIsbn());
                bookStore.log.new Success("Isbns matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("Titles not match",userBook.getTitle(),book.getTitle());
                bookStore.log.new Success("Book titles matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("SubTitles not match", book.getSubTitle(), userBook.getSubTitle());
                bookStore.log.new Success("SubTitles matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("Publisher not match", book.getPublisher(), userBook.getPublisher());
                bookStore.log.new Success("Publishers matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("Description not match", book.getDescription(), userBook.getDescription());
                bookStore.log.new Success("Descriptions matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("Author not match", book.getAuthor(), userBook.getAuthor());
                bookStore.log.new Success("Authors matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("Website not match", book.getWebsite(), userBook.getWebsite());
                bookStore.log.new Success("Websites matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("Pages not match", book.getPages(), userBook.getPages());
                bookStore.log.new Success("Pages matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));

                Assert.assertEquals("Publish date not match", book.getPublish_date(), userBook.getPublish_date());
                bookStore.log.new Success("Publish date matches for book named: " + strUtils.markup(BLUE, userBook.getTitle()));
            }
    }

}
