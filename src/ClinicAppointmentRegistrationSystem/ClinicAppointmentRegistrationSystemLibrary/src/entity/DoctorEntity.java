/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import util.exception.DoctorAddAppointmentException;
import util.exception.DoctorAddConsultationException;
import util.exception.DoctorRemoveAppointmentException;

@Entity
public class DoctorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doctorId;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false)
    private String registration;
    @Column(length = 32, nullable = false)
    private String qualifications;

    @OneToMany(mappedBy = "doctorC")
    @JoinColumn(nullable = false)
    private List<ConsultationEntity> consultationEntities;

    @JoinColumn(nullable = false)
    @OneToMany(mappedBy = "doctorA")
    private List<AppointmentEntity> appointmentEntities;

    public DoctorEntity() {

        this.appointmentEntities = new ArrayList<>();
        this.consultationEntities = new ArrayList<>();
    }

    public DoctorEntity(String firstName, String lastName, String registration) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.registration = registration;
    }

    public DoctorEntity(Long doctorId, String firstName, String lastName, String registration, String qualifications) {
        this();

        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registration = registration;
        this.qualifications = qualifications;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.doctorId != null ? this.doctorId.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DoctorEntity)) {
            return false;
        }

        DoctorEntity other = (DoctorEntity) object;

        if ((this.doctorId == null && other.doctorId != null) || (this.doctorId != null && !this.doctorId.equals(other.doctorId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "entity.DoctorEntity[ doctorId=" + this.doctorId + " ]";
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRegistration() {
        return registration;
    }

    public void setUserName(String registration) {
        this.registration = registration;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public List<ConsultationEntity> getConsultationEntities() {
        return consultationEntities;
    }

    public void setConsultationEntities(List<ConsultationEntity> consultationEntities) {
        this.consultationEntities = consultationEntities;
    }

    public void addConsultation(ConsultationEntity consultation) throws DoctorAddConsultationException {
        if (consultation != null && !this.getConsultationEntities().contains(consultation)) {
            this.getConsultationEntities().add(consultation);
        } else {
            throw new DoctorAddConsultationException("Consultation already added to Doctor");
        }
    }

    /*
        public void removeConsultation(ConsultationEntity consultation) throws DoctorRemoveConsultationException
    {
        if(consultation != null && this.consultationEntities.contains(consultation))
        {
            this.getConsultationEntities().remove(consultation);
        }
        else
        {
            throw new DoctorRemoveConsultationException("Consultation has not been added to Doctor");
        }
    }*/
    public List<AppointmentEntity> getAppointmentEntities() {
        return appointmentEntities;
    }

    public void setAppointmentEntities(List<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities = appointmentEntities;
    }

    public void addAppointment(AppointmentEntity appointment) throws DoctorAddAppointmentException {
        if (appointment != null && !this.getAppointmentEntities().contains(appointment)) {
            this.getAppointmentEntities().add(appointment);
        } else {
            throw new DoctorAddAppointmentException("Appointment already added to Doctor");
        }
    }

    public void removeAppointment(AppointmentEntity appointment) throws DoctorRemoveAppointmentException {
        if (appointment != null && this.appointmentEntities.contains(appointment)) {
            this.getAppointmentEntities().remove(appointment);
        } else {
            throw new DoctorRemoveAppointmentException("Appointment has not been added to Doctor");
        }
    }
}
