package repositories.interfaces;

import models.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    void createBook(Book book) throws SQLException;

    Book getBookById(int id) throws SQLException;

    List<Book> getAllBooks() throws SQLException;

    void updateBook(Book book) throws SQLException;

    void deleteBook(int id) throws SQLException;
}
