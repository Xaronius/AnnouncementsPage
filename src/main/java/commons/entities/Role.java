package commons.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "ROLE_013")
@Setter
public class Role {
    public static final String ID_013 = "ID_013";
    public static final String ISACTIVE_013 = "IS_ACTIVE_013";
    public static final String NAME_013 = "NAME_013";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isActive;
    private String name;

    @Column(name = ID_013, nullable = false)
    public Long getId() { return this.id; }

    @Column(name = ISACTIVE_013, nullable = false)
    public Boolean getIsActive() { return this.isActive; }

    @Column(name = NAME_013, nullable = false)
    public String getName() { return this.name; }
}
