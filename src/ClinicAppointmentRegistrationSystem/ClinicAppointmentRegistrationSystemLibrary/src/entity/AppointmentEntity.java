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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AppointmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appointmentId;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private PatientEntity patientA;
    // private String identityNumber;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private DoctorEntity doctorA;
    @Temporal(TemporalType.DATE)
    private Date date; //YYYY-MM-DD
    @Temporal(TemporalType.TIME)
    private Date time; //HH:MM

    public AppointmentEntity() {

    }

    public AppointmentEntity(Long appointmentId, Date date, Date time) {
        this();

        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public PatientEntity getPatient() {
        return patientA;
    }

    public void setPatient(PatientEntity patient) {
        this.patientA = patient;
    }

    public DoctorEntity getDoctor() {
        return doctorA;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctorA = doctor;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
