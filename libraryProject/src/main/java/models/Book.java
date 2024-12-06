package models;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String title;
    private Author author;
    private Genre genre;
    private String description;
    private int publicationYear;
    private String isbn;
    private int availableCopies;
    private int totalCopies;
}
