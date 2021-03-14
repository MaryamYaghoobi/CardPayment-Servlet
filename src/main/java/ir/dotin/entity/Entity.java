package ir.dotin.entity;

import javax.persistence.*;


@MappedSuperclass
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(columnDefinition = "BIT", length = 1)
    private Boolean disabled;
    @Column(columnDefinition = "BIT", length = 1)
    private Boolean active;
    @Version
    private Long version;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


}