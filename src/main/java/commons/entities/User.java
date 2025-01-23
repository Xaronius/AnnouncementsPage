
package commons.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "USERS_010")
public class User {
    private static final int LOGIN_LENGTH = 32;
    public static final String ID = "ID_010";
    public static final String LOGIN = "LOGIN_010";
    public static final String PASSWORD = "PASSWORD_011_010";
    public static final String EMAIL = "EMAIL_010";
    public static final String ROLE = "ROLE_010";
    public static final String IS_ADMIN = "IS_ADMIN_010";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;
    @Setter
    private String login;
    @Setter
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private Password password;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Role role;

    @Setter
    private Boolean isAdmin;

    @Column(name = ID, nullable = false)
    public Long getId() { return this.id; }

    @Column(name = LOGIN, length = LOGIN_LENGTH, nullable = false)
    public String getLogin() { return this.login; }

    @Column(name = EMAIL, nullable = false)
    public String getEmail() { return this.email; }

    @JoinColumn(name = "ID_011")
    @Column(name = PASSWORD, nullable = false)
    public Password getPassword() { return this.password; }

    @JoinColumn(name = "ID_013")
    @Column(name = ROLE)
    public Role getRole() { return this.role; }

    @Column(name = IS_ADMIN)
    public Boolean getIsAdmin() { return this.isAdmin; }
}