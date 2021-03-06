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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class DoctorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    @NotNull
    @Size(min = 1, max = 32)
    @Column(length = 32, nullable = false)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 32)
    @Column(length = 32, nullable = false)
    private String lastName;
    @NotNull
    @Size(min = 6, max = 6)
    @Column(length = 6, nullable = false)
    private String registration;
    @NotNull
    @Size(min = 1, max = 20)
    @Column(length = 20, nullable = false)
    private String qualifications;

    @OneToMany(mappedBy = "consultingDoctor")
    @JoinColumn(nullable = false)
    private List<ConsultationEntity> consultationEntities;

    @JoinColumn(nullable = false)
    @OneToMany(mappedBy = "appointmentDoctor")
    private List<AppointmentEntity> appointmentEntities;
    
    @JoinColumn(nullable = false)
    @OneToMany(mappedBy = "leaveDoctor")
    private List<LeaveEntity> leaveEntities;

    public DoctorEntity() {
        this.appointmentEntities = new ArrayList<>();
        this.consultationEntities = new ArrayList<>();
        this.leaveEntities = new ArrayList<>();
    }

    public DoctorEntity(String firstName, String lastName, String registration,String qualifications) {
        this();
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

    public List<AppointmentEntity> getAppointmentEntities() {
        return appointmentEntities;
    }

    public void setAppointmentEntities(List<AppointmentEntity> appointmentEntities) {
        this.appointmentEntities = appointmentEntities;
    }

    public List<LeaveEntity> getLeaveEntities() {
        return leaveEntities;
    }

    public void setLeaveEntities(List<LeaveEntity> leaveEntities) {
        this.leaveEntities = leaveEntities;
    }
    
}
