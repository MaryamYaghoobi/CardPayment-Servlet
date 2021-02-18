
package ir.dotin.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Email")
@Table(name = "t_Email")
public class Email extends Common {


    @Column(name = "c_context", columnDefinition = "VARCHAR(255)")
    private String context;

    @OneToMany ()
    @JoinTable(name = "mm_EmployeeEmail",
            joinColumns = {@JoinColumn(name = "c_receiveEmailId")},
            inverseJoinColumns = {@JoinColumn(name = "c_receiverId")})
    private List<Employee> receiverEmployees;

    @ManyToOne()
    @JoinColumn(columnDefinition = "c_employeeSenderId")
    private List<Employee> senderEmail;

    public List<Employee> getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(List<Employee> senderEmail) {
        this.senderEmail = senderEmail;
    }


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public List<Employee> getReceiverEmployees() {
        return receiverEmployees;
    }

    public void setReceiverEmployees(List<Employee> receiverEmployees) {
        this.receiverEmployees = receiverEmployees;
    }


}