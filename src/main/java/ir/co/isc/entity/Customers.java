package ir.co.isc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity(name = "Customers")
@Table(name = "t_Customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
public class Customers extends BaseEntity implements Serializable {
    @Column(name = "c_nationalCode", columnDefinition = "VARCHAR(10)")
    private String nationalCode;
    @Column(name = "c_firstName", columnDefinition = "VARCHAR(255)")
    private String firstName;
    @Column(name = "c_lastName", columnDefinition = "VARCHAR(255)")
    private String lastName;
    @Column(name = "c_email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "c_accountNumber", columnDefinition = "VARCHAR(10)")
    private String accountNumber;
    @ManyToOne()
    @JoinColumn(name = "c_banker")
    private Customers banker;
    @OneToMany(mappedBy = "banker", cascade = CascadeType.ALL)
    private List<Customers> customers;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_role")
    private CategoryElement role;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_customerId")
    private List<Cards> cardsList;
}