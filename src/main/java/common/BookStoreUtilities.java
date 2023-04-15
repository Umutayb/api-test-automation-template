package common;

import models.bookstore.BookListModel;
import models.bookstore.BookModel;

public class BookStoreUtilities {
    public static String acquireBook(String label, BookListModel books) {
        for (BookModel book: books.getBooks())
            if (book.getPublisher().equalsIgnoreCase(label))
                return book.getIsbn();
        throw new RuntimeException("Book is not found");
    }
}
