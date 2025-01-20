package commons.entities;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "PASSWORD_011")
@Setter
public class Password {
    public static final String ID = "ID_011";
    public static final String HASH_011 = "HASH_011";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;

    @Column(name = ID, nullable = false)
    public Long getId() { return this.id; }

    @Column(name = HASH_011, nullable = false)
    public String getHash() { return this.hash; }
}
