package ir.dotin.entity;

import javax.persistence.*;
import java.io.Serializable;


//@Entity(name = "Common")
//@Table(name = "t_Common")
@MappedSuperclass
/*@Entity(name="products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="product_type",
        discriminatorType = DiscriminatorType.INTEGER)*/
public class entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(columnDefinition = "boolean default false")
    private Boolean disabled;
    @Column(columnDefinition = "boolean default false")
    private Boolean active;
    @Version
    private long c_version;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public long getC_version() {
        return c_version;
    }

    public void setC_version(long c_version) {
        this.c_version = c_version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
