
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author leege
 */
@Entity
public class QueueTicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Temporal(TemporalType.DATE)
    private Date currentDate;
    private int queueNo;

    public QueueTicketEntity() {
    }

    public QueueTicketEntity(Date currentDate) {
        this.currentDate = currentDate;
    }


    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QueueTicketEntity)) {
            return false;
        }
        QueueTicketEntity other = (QueueTicketEntity) object;
        if ((this.currentDate == null && other.currentDate != null) || (this.currentDate != null && !this.currentDate.equals(other.currentDate))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.currentDate);
        return hash;
    }

    @Override
    public String toString() {
        return "entity.QueueTicketEntity[ date=" + currentDate.toString() + " ]";
    }

    /**
     * @return the currentDate
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * @return the queueNo
     */
    public int getQueueNo() {
        return queueNo;
    }

    /**
     * @param aQueueNo the queueNo to set
     */
    public void setQueueNo(int aQueueNo) {
        queueNo = aQueueNo;
    }
    
}
