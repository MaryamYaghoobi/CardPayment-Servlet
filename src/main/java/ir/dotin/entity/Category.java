package ir.dotin.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "Category")
@Table(name = "t_Category")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Category extends Common {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255)")
    private String name;
    @OneToMany()
    @JoinColumn(name = "c_categoryElementList")
    private List<CategoryElement> id;



    public List<CategoryElement> getCategoryElementList() {
        return id;
    }

    public void setCategoryElementList(List<CategoryElement> categoryElementList) {
        this.id = categoryElementList;
    }

    public String getInstance() {
        return name;
    }

    public void setInstance(String instance) {
        this.name = name;
    }

    public Category() {
    }

}
