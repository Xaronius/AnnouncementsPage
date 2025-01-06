package commons.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "PASSWORD_011")
public class Password {
    public static final String ID = "ID_011";
    public static final String USER_010 = "USER_010_011";
    public static final String SEED_011 = "SEED_011";
    public static final String PASSWORD_011 = "USER_PASSWORD_011";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;
    @Setter
    private String seed;
    @Setter
    private Long userId;
    @Setter
    private String password;

    @Id
    @Column(name = ID)
    public Long getId() { return this.id; }

    @Column(name = PASSWORD_011)
    public String getPassword() { return this.password; }

    @Column(name = SEED_011)
    public String getSeed() { return this.seed; }

    @Column(name = USER_010)
    public Long getUserId() { return this.userId; }
}
