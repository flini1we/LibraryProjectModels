package models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private LocalDateTime registrationDate;
}