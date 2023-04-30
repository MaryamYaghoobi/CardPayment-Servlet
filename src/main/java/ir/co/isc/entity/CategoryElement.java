package ir.co.isc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity(name = "CategoryElement")
@Table(name = "t_CategoryElement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryElement extends ir.co.isc.entity.Entity {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "c_code", columnDefinition = "VARCHAR(255)")
    private String code;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<Category> category;
}
