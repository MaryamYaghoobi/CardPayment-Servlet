package ir.co.isc.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "Category")
@Table(name = "t_Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity implements Serializable {

    @Column(name = "c_name", columnDefinition = "VARCHAR(255)")
    private String name;

}
