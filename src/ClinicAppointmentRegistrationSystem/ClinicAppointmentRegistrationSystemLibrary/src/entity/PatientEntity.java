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
import util.exception.PatientAddAppointmentException;
import util.exception.PatientAddConsultationException;
import util.exception.PatientRemoveAppointmentException;

@Entity
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 9, unique = true, nullable = false)
    private String identityNumber;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long patientId;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 1, nullable = false)
    private String gender;
    @Column(length = 32, nullable = false)
    private String securityCode;
    @Column(length = 3, nullable = false)
    private Integer age;
    @Column(length = 8, nullable = false)
    private String phone;
    @Column(length = 32, nullable = false)
    private String address;

    @OneToMany(mappedBy = "consultingPatient")
    @JoinColumn(nullable = false)
    private List<ConsultationEntity> consultationEntities;

    @OneToMany(mappedBy = "appointmentPatient")
    @JoinColumn(nullable = false)
    private List<AppointmentEntity> appointmentEntities;

    public PatientEntity() {

        this.appointmentEntities = new ArrayList<>();
        this.consultationEntities = new ArrayList<>();
    }

    public PatientEntity(String firstName, String lastName, String identityNumber, String securityCode) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNumber = identityNumber;
        this.securityCode = securityCode;
    }

    public PatientEntity(Long patientId, String firstName, String lastName, String gender, String securityCode, Integer age, String identityNumber, String phone, String address) {
        this();

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.securityCode = securityCode;
        this.age = age;
        this.identityNumber = identityNumber;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.patientId != null ? this.patientId.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PatientEntity)) {
            return false;
        }

        PatientEntity other = (PatientEntity) object;

        if ((this.patientId == null && other.patientId != null) || (this.patientId != null && !this.patientId.equals(other.patientId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "entity.PatientEntity[ patientId=" + this.patientId + " ]";
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String ecurityCode) {
        this.securityCode = ecurityCode;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ConsultationEntity> getConsultationEntities() {
        return consultationEntities;
    }

    public void setConsultationEntities(List<ConsultationEntity> consultationEntities) {
        this.consultationEntities = consultationEntities;
    }

    public void addConsultation(ConsultationEntity consultation) throws PatientAddConsultationException {
        if (consultation != null && !this.getConsultationEntities().contains(consultation)) {
            this.getConsultationEntities().add(consultation);
        } else {
            throw new PatientAddConsultationException("Consultation already added to Patient");
        }
    }
    
    public List<AppointmentEntity> getAppointmentEntities() {
        return appointmentEntities;
    }

    public void setAppointmentEntities(List<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities = appointmentEntities;
    }

    public void addAppointment(AppointmentEntity appointment) throws PatientAddAppointmentException {
        if (appointment != null && !this.getAppointmentEntities().contains(appointment)) {
            this.getAppointmentEntities().add(appointment);
        } else {
            throw new PatientAddAppointmentException("Appointment already added to Patient");
        }
    }

    public void removeAppointment(AppointmentEntity appointment) throws PatientRemoveAppointmentException {
        if (appointment != null && this.appointmentEntities.contains(appointment)) {
            this.getAppointmentEntities().remove(appointment);
        } else {
            throw new PatientRemoveAppointmentException("Appointment has not been added to Patient");
        }
    }
}
