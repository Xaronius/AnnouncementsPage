package commons.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "ROLES_PRIVILEGES_013_012_0013")
@Setter
public class RolesPrivileges {

    public static final String ID_0013 = "ID_0013";
    public static final String ROLE_013_0013 = "ROLE_013_0013";
    public static final String PRIVILEGE_012_0013 = "PRIVILEGE_013_0013";
    public static final String USER_010_0013 = "USER_010_0013";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Role role;
    @ManyToOne
    private Privileges privilege;
    @ManyToOne
    private User user;

    @Column(name = ID_0013, nullable = false)
    public Long getId() { return this.id; }

    @JoinColumn(name = "ID_013")
    @Column(name = ROLE_013_0013, nullable = false)
    public Role getRole() { return this.role; }

    @JoinColumn(name = "ID_012")
    @Column(name = PRIVILEGE_012_0013, nullable = false)
    public Privileges getPrivilege() { return this.privilege; }

    @JoinColumn(name = "ID_010")
    @Column(name = USER_010_0013, nullable = false)
    public User getUser() {return this.user; }
}
