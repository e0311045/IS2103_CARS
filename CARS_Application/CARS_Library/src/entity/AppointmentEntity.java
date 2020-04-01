
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author leege
 */
@Entity
public class AppointmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @Temporal (TemporalType.DATE)
    @Column(nullable = false)
    private Date apptDate;
    @Temporal (TemporalType.TIME)
    @Column(nullable = false)
    private Time apptTime;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private DoctorEntity doctor;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private PatientEntity patient;
    

    public AppointmentEntity() {
    }

    public AppointmentEntity(Date apptDate, Time apptTime, DoctorEntity doctor, PatientEntity patient) {
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.doctor = doctor;
        this.patient = patient;
    }
    
    

    public AppointmentEntity(Date date) {
        this.apptDate = date;
    }

    
    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentId != null ? appointmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the appointmentId fields are not set
        if (!(object instanceof AppointmentEntity)) {
            return false;
        }
        AppointmentEntity other = (AppointmentEntity) object;
        if ((this.appointmentId == null && other.appointmentId != null) || (this.appointmentId != null && !this.appointmentId.equals(other.appointmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AppointmentEntity[ id=" + appointmentId + " ]";
    }

    /**
     * @return the doctor
     */
    public DoctorEntity getDoctor() {
        return doctor;
    }

    /**
     * @param doctor the doctor to set
     */
    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    /**
     * @return the patient
     */
    public PatientEntity getPatient() {
        return patient;
    }

    /**
     * @param patient the patient to set
     */
    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    /**
     * @return the apptDate
     */
    public Date getApptDate() {
        return apptDate;
    }

    /**
     * @param apptDate the apptDate to set
     */
    public void setApptDate(Date apptDate) {
        this.apptDate = apptDate;
    }

    /**
     * @return the apptTime
     */
    public Time getApptTime() {
        return apptTime;
    }

    /**
     * @param apptTime the apptTime to set
     */
    public void setApptTime(Time apptTime) {
        this.apptTime = apptTime;
    }
    
}
