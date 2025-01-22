package commons.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "PRIVILEGES_012")
@Setter
public class Privileges {
    public static final String ID_012 = "ID_012";
    public static final String ISACTIVE_012 = "IS_ACTIVE_012";
    public static final String NAME_012 = "NAME_012";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isActive;
    private String name;

    @Column(name = ID_012, nullable = false)
    public Long getId() { return this.id; }

    @Column(name = ISACTIVE_012, nullable = false)
    public Boolean getIsActive() { return this.isActive; }

    @Column(name = NAME_012, nullable = false)
    public String getName() { return this.name; }
}
