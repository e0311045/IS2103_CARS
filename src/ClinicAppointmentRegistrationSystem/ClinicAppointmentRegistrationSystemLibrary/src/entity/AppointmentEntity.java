/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Future;

@Entity
public class AppointmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @Future
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date appointmentDate; //Format Kept YYYY-MM-DD
    @Future
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date appointmentTime; //Format Kept HH:MM
    
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity appointmentPatient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity appointmentDoctor;


    public AppointmentEntity() {

    }

    public AppointmentEntity(Long appointmentId, Date date, Date time) {
        this();

        this.appointmentId = appointmentId;
        this.appointmentDate = date;
        this.appointmentTime = time;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public PatientEntity getPatient() {
        return appointmentPatient;
    }

    public void setPatient(PatientEntity patient) {
        this.appointmentPatient = patient;
    }

    public DoctorEntity getDoctor() {
        return appointmentDoctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.appointmentDoctor = doctor;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
