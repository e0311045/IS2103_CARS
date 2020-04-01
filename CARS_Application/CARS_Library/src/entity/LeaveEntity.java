
package entity;

import java.io.Serializable;
import java.util.Date;
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
public class LeaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateOnLeave;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private DoctorEntity doctorEntity;

    public LeaveEntity() {
    }

    public LeaveEntity(Date dateOnLeave, DoctorEntity doctorEntity) {
        this.dateOnLeave = dateOnLeave;
        this.doctorEntity = doctorEntity;
    }

    
    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leaveId != null ? leaveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the leaveId fields are not set
        if (!(object instanceof LeaveEntity)) {
            return false;
        }
        LeaveEntity other = (LeaveEntity) object;
        if ((this.leaveId == null && other.leaveId != null) || (this.leaveId != null && !this.leaveId.equals(other.leaveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LeaveEntity[ id=" + leaveId + " ]";
    }

    /**
     * @return the dateOnLeave
     */
    public Date getDateOnLeave() {
        return dateOnLeave;
    }

    /**
     * @param dateOnLeave the dateOnLeave to set
     */
    public void setDateOnLeave(Date dateOnLeave) {
        this.dateOnLeave = dateOnLeave;
    }

    /**
     * @return the doctorEntity
     */
    public DoctorEntity getDoctorEntity() {
        return doctorEntity;
    }

    /**
     * @param doctorEntity the doctorEntity to set
     */
    public void setDoctorEntity(DoctorEntity doctorEntity) {
        this.doctorEntity = doctorEntity;
    }
    
}
