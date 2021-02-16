package ir.dotin.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "Category")
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "LONG", nullable = false, unique = true)
    public long id;
    @Column(name = "instance", columnDefinition = "VARCHAR(255)")
    private String instance;
    @ManyToOne
    @JoinColumn(name = "categoryElementList")
    private Set<CategoryElement> categoryElementList = new HashSet<CategoryElement>();

    public Set<CategoryElement> getCategoryElementList() {
        return categoryElementList;
    }

    public void setCategoryElementList(Set<CategoryElement> categoryElementList) {
        this.categoryElementList = categoryElementList;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category() {
    }

}
