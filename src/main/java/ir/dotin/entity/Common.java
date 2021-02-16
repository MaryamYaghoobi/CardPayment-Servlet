package ir.dotin.entity;

import javax.persistence.*;


@Entity(name = "Common")
@Table(name = "t_Common")
public class Common {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
