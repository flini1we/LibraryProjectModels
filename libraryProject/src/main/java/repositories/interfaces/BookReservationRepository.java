package repositories.interfaces;

import models.BookReservation;

import java.sql.SQLException;
import java.util.List;

public interface BookReservationRepository {
    void createReservation(BookReservation reservation) throws SQLException;

    BookReservation getReservationById(int id) throws SQLException;

    List<BookReservation> getAllReservations() throws SQLException;

    void updateReservation(BookReservation reservation) throws SQLException;

    void deleteReservation(int id) throws SQLException;
}
