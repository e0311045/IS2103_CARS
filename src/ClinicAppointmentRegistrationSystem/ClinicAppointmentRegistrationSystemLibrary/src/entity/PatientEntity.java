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

@Entity
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 9, unique = true, nullable = false)
    private String identityNumber;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long patientId;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(length = 32, nullable = false)
    private String firstName;
    @Column(length = 32, nullable = false)
    private String lastName;
    @Column(length = 1, nullable = false)
    private char gender;
    @Column(length = 3, nullable = false)
    private Integer age;
    @Column(length = 15, nullable = false)
    private String phone;
    @Column(length = 50, nullable = false)
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

//    public PatientEntity(String firstName, String lastName, String identityNumber, String securityCode) {
//        this();
//
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.identityNumber = identityNumber;
//        this.securityCode = securityCode;
//    }

    public PatientEntity(String identityNumber, String password, String firstName, String lastName, char gender, Integer age,  String phone, String address) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.password = password;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void addConsultation(ConsultationEntity consultation) {
            this.getConsultationEntities().add(consultation);
    }
    
    public List<AppointmentEntity> getAppointmentEntities() {
        return appointmentEntities;
    }

    public void setAppointmentEntities(List<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities = appointmentEntities;
    }
    
    public void addAppointment(AppointmentEntity appointment) {
            this.getAppointmentEntities().add(appointment);

    }

    public void removeAppointment(AppointmentEntity appointment) {
            this.getAppointmentEntities().remove(appointment);
    }

}
