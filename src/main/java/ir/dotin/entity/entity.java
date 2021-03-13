package ir.dotin.entity;

import javax.persistence.*;


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
    private Boolean c_disabled;

    public Boolean getC_disabled() {
        return c_disabled;
    }

    public void setC_disabled(Boolean c_disabled) {
        this.c_disabled = c_disabled;
    }

    public Boolean getC_active() {
        return c_active;
    }

    public void setC_active(Boolean c_active) {
        this.c_active = c_active;
    }

    @Column(columnDefinition = "boolean default false")
    private Boolean c_active;
    @Version
    private long c_version;


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

}
