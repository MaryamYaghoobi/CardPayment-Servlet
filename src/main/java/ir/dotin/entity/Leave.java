package ir.dotin.entity;

import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "Leave")
@Table(name = "Leave")
@SelectBeforeUpdate
public class Leave implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Column(columnDefinition = "LONG",  nullable = false , unique = true)
    public long id;
    @Column(name = "leaveNum", columnDefinition = "LONG")
    private String leaveNum;
    @Column(name = "status", columnDefinition = "BOOLEAN")
    private String status;
    @OneToOne
    @JoinColumn(name="registrationId")
    private Registration registration;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLeaveNum() {
        return leaveNum;
    }

    public void setLeaveNum(String leaveNum) {
        this.leaveNum = leaveNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }



}