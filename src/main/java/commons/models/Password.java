package commons.models;

import commons.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = Password.TABLE_NAME)
public class Password {

    public static final String ID = "ID_011";
    public static final String USER_010 = "USER_010_011";
    public static final String SEED_011 = "SEED_011";
    public static final String PASSWORD_011 = "PASSWORD_011";
    public static final String TABLE_NAME = "TABLE_NAME_011";

    private Long id;
    private String seed;
    private User user;
    private String password;

    @Id
    @Column(name = ID)
    public Long getId() { return this.getId(); }

    @Column(name = PASSWORD_011)
    public String getPassword() { return getPassword(); }

    @Column(name = SEED_011)
    public String getSeed() { return this.getSeed(); }

    @Column(name = USER_010)
    public User getUser() { return this.getUser(); }
}
