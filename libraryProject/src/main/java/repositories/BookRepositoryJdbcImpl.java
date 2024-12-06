package repositories;

import models.Author;
import models.Book;
import models.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryJdbcImpl implements repositories.interfaces.BookRepository {
    private final Connection connection;

    private static final String INSERT_BOOK = "INSERT INTO books (title, author_id, genre_id, description, publication_year, isbn, available_copies, total_copies) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BOOK_BY_ID = "SELECT b.*, a.name AS author_name, g.name AS genre_name " +
            "FROM books b " +
            "JOIN authors a ON b.author_id = a.id " +
            "JOIN genres g ON b.genre_id = g.id " +
            "WHERE b.id = ?";
    private static final String SELECT_ALL_BOOKS = "SELECT b.*, a.name AS author_name, g.name AS genre_name " +
            "FROM books b " +
            "JOIN authors a ON b.author_id = a.id " +
            "JOIN genres g ON b.genre_id = g.id";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author_id = ?, genre_id = ?, description = ?, publication_year = ?, isbn = ?, available_copies = ?, total_copies = ? WHERE id = ?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";

    public BookRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createBook(Book book) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_BOOK)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getAuthor().getId());
            stmt.setInt(3, book.getGenre().getId());
            stmt.setString(4, book.getDescription());
            stmt.setInt(5, book.getPublicationYear());
            stmt.setString(6, book.getIsbn());
            stmt.setInt(7, book.getAvailableCopies());
            stmt.setInt(8, book.getTotalCopies());
            stmt.executeUpdate();
        }
    }

    @Override
    public Book getBookById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToBook(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_BOOKS)) {
            while (rs.next()) {
                books.add(mapToBook(rs));
            }
        }
        return books;
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_BOOK)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getAuthor().getId());
            stmt.setInt(3, book.getGenre().getId());
            stmt.setString(4, book.getDescription());
            stmt.setInt(5, book.getPublicationYear());
            stmt.setString(6, book.getIsbn());
            stmt.setInt(7, book.getAvailableCopies());
            stmt.setInt(8, book.getTotalCopies());
            stmt.setInt(9, book.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteBook(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_BOOK)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Book mapToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));

        Author author = new Author();
        author.setId(rs.getInt("author_id"));
        author.setName(rs.getString("author_name"));
        book.setAuthor(author);

        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("genre_name"));
        book.setGenre(genre);

        book.setDescription(rs.getString("description"));
        book.setPublicationYear(rs.getInt("publication_year"));
        book.setIsbn(rs.getString("isbn"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        book.setTotalCopies(rs.getInt("total_copies"));
        return book;
    }
}