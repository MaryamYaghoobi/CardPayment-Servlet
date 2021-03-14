package ir.dotin.entity;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "Category")
@Table(name = "t_Category")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Category extends ir.dotin.entity.Entity {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255)")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() {
    }

}
