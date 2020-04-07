
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Future;


@Entity
public class LeaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;
    @Future
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)    
    private Date leaveDate; //Format Kept YYYY-MM-DD
    
    @NotNull
    @Size(min = 1, max = 2)
    @Column(length = 2, nullable = false)
    private int weekNo;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity leaveDoctor;

    public LeaveEntity() {
    }

    public LeaveEntity(Date leaveDate, int weekNo, DoctorEntity onLeaveDoctor) {
        this.leaveDate = leaveDate;
        this.weekNo = weekNo;
        this.leaveDoctor = onLeaveDoctor;
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

 
    public Date getLeaveDate() {
        return leaveDate;
    }


    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }


    public int getWeekNo() {
        return weekNo;
    }


    public void setWeekNo(int weekNo) {
        this.weekNo = weekNo;
    }


    public DoctorEntity getLeaveDoctor() {
        return leaveDoctor;
    }


    public void setLeaveDoctor(DoctorEntity leaveDoctor) {
        this.leaveDoctor = leaveDoctor;
    }
    
}
