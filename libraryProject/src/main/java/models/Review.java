package models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private int id;
    private Book book;
    private User user;
    private int rating;
    private String comment;
    private LocalDateTime reviewDate;
}
