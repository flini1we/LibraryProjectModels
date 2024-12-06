package models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookReservation {
    private int id;
    private Book book;
    private User user;
    private LocalDateTime reservationDate;
    private LocalDateTime returnDate;
    private String status;
}
