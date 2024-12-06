package repositories;

import models.Book;
import models.BookReservation;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookReservationRepositoryJdbcImpl implements repositories.interfaces.BookReservationRepository {
    private final Connection connection;

    private static final String INSERT_RESERVATION = "INSERT INTO book_reservations (book_id, user_id, reservation_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_RESERVATION_BY_ID = "SELECT * FROM book_reservations WHERE id = ?";
    private static final String SELECT_ALL_RESERVATIONS = "SELECT * FROM book_reservations";
    private static final String UPDATE_RESERVATION = "UPDATE book_reservations SET book_id = ?, user_id = ?, reservation_date = ?, return_date = ?, status = ? WHERE id = ?";
    private static final String DELETE_RESERVATION = "DELETE FROM book_reservations WHERE id = ?";

    public BookReservationRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createReservation(BookReservation reservation) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_RESERVATION)) {
            stmt.setInt(1, reservation.getBook().getId());
            stmt.setInt(2, reservation.getUser().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getReservationDate()));
            stmt.setTimestamp(4, reservation.getReturnDate() != null ? Timestamp.valueOf(reservation.getReturnDate()) : null);
            stmt.setString(5, reservation.getStatus());
            stmt.executeUpdate();
        }
    }

    @Override
    public BookReservation getReservationById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_RESERVATION_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToReservation(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<BookReservation> getAllReservations() throws SQLException {
        List<BookReservation> reservations = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_RESERVATIONS)) {
            while (rs.next()) {
                reservations.add(mapToReservation(rs));
            }
        }
        return reservations;
    }

    @Override
    public void updateReservation(BookReservation reservation) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_RESERVATION)) {
            stmt.setInt(1, reservation.getBook().getId());
            stmt.setInt(2, reservation.getUser().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getReservationDate()));
            stmt.setTimestamp(4, reservation.getReturnDate() != null ? Timestamp.valueOf(reservation.getReturnDate()) : null);
            stmt.setString(5, reservation.getStatus());
            stmt.setInt(6, reservation.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteReservation(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_RESERVATION)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private BookReservation mapToReservation(ResultSet rs) throws SQLException {
        BookReservation reservation = new BookReservation();
        reservation.setId(rs.getInt("id"));

        User user = new User();
        user.setId(rs.getInt("user_id"));
        reservation.setUser(user);

        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        reservation.setBook(book);

        reservation.setReservationDate(rs.getTimestamp("reservation_date").toLocalDateTime());
        if (rs.getTimestamp("return_date") != null) {
            reservation.setReturnDate(rs.getTimestamp("return_date").toLocalDateTime());
        }
        reservation.setStatus(rs.getString("status"));

        return reservation;
    }
}