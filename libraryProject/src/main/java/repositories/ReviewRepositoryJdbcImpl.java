package repositories;

import models.Review;
import models.Book;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryJdbcImpl implements repositories.interfaces.ReviewRepository {
    private final Connection connection;

    private static final String INSERT_REVIEW = "INSERT INTO reviews (book_id, user_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_REVIEW_BY_ID = "SELECT * FROM reviews WHERE id = ?";
    private static final String SELECT_ALL_REVIEWS = "SELECT * FROM reviews";
    private static final String UPDATE_REVIEW = "UPDATE reviews SET book_id = ?, user_id = ?, rating = ?, comment = ?, review_date = ? WHERE id = ?";
    private static final String DELETE_REVIEW = "DELETE FROM reviews WHERE id = ?";

    public ReviewRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createReview(Review review) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_REVIEW)) {
            stmt.setInt(1, review.getBook().getId());
            stmt.setInt(2, review.getUser().getId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.setTimestamp(5, Timestamp.valueOf(review.getReviewDate()));
            stmt.executeUpdate();
        }
    }

    @Override
    public Review getReviewById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_REVIEW_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToReview(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_REVIEWS)) {
            while (rs.next()) {
                reviews.add(mapToReview(rs));
            }
        }
        return reviews;
    }

    @Override
    public void updateReview(Review review) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_REVIEW)) {
            stmt.setInt(1, review.getBook().getId());
            stmt.setInt(2, review.getUser().getId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.setTimestamp(5, Timestamp.valueOf(review.getReviewDate()));
            stmt.setInt(6, review.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteReview(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_REVIEW)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Review mapToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));

        User user = new User();
        user.setId(rs.getInt("user_id"));
        review.setUser(user);

        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        review.setBook(book);

        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setReviewDate(rs.getTimestamp("review_date").toLocalDateTime());

        return review;
    }
}