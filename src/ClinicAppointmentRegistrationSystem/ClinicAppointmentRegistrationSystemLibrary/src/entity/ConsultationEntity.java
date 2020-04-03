/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ConsultationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consultationId;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private PatientEntity patientC;
    // private String identityNumber;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private DoctorEntity doctorC;
    @Temporal(TemporalType.TIME)
    private Date time; //HH:MM

    public ConsultationEntity() {

    }

    public ConsultationEntity(Long consultationId, Date time) {
        this();

        this.consultationId = consultationId;
        this.time = time;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public PatientEntity getPatient() {
        return patientC;
    }

    public void setPatient(PatientEntity patient) {
        this.patientC = patient;
    }

    public DoctorEntity getDoctor() {
        return doctorC;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctorC = doctor;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
