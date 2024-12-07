package commons.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name=User.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = User.ID)})

public class User {

    private static final int LOGIN_LENGTH = 32;
    public static final String TABLE_NAME = "USERS_010";
    public static final String ID = "ID_010";
    public static final String LOGIN = "LOGIN_010";
    public static final String EMAIL = "EMAIL_010";

    private Long id;
    private String login;
    private String email;

    @Id
    @Column(name = ID, nullable = false)
    public Long getId() { return this.id; }

    @Column(name = LOGIN, length = LOGIN_LENGTH, nullable = false)
    public String getLogin() { return this.login; }

    @Column(name = EMAIL, nullable = false)
    public String getEmail() { return this.email; }
}
