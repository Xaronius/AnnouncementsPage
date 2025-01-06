package commons.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "USERS_010")
public class User {
    private static final int LOGIN_LENGTH = 32;
    public static final String ID = "ID_010";
    public static final String LOGIN = "LOGIN_010";
    public static final String EMAIL = "EMAIL_010";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;
    @Setter
    private String login;
    @Setter
    private String email;

    @Id
    @Column(name = ID, nullable = false)
    public Long getId() { return this.id; }

    @Column(name = LOGIN, length = LOGIN_LENGTH, nullable = false)
    public String getLogin() { return this.login; }

    @Column(name = EMAIL, nullable = false)
    public String getEmail() { return this.email; }
}
