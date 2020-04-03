
package amsterminalclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for doctorEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="doctorEntity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="appointmentEntities" type="{http://ws.session.ejb/}appointmentEntity" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="consultationEntities" type="{http://ws.session.ejb/}consultationEntity" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="doctorId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="qualifications" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doctorEntity", propOrder = {
    "appointmentEntities",
    "consultationEntities",
    "doctorId",
    "firstName",
    "lastName",
    "qualifications"
})
public class DoctorEntity {

    @XmlElement(nillable = true)
    protected List<AppointmentEntity> appointmentEntities;
    @XmlElement(nillable = true)
    protected List<ConsultationEntity> consultationEntities;
    protected Long doctorId;
    protected String firstName;
    protected String lastName;
    protected String qualifications;

    /**
     * Gets the value of the appointmentEntities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appointmentEntities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppointmentEntities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AppointmentEntity }
     * 
     * 
     */
    public List<AppointmentEntity> getAppointmentEntities() {
        if (appointmentEntities == null) {
            appointmentEntities = new ArrayList<AppointmentEntity>();
        }
        return this.appointmentEntities;
    }

    /**
     * Gets the value of the consultationEntities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consultationEntities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsultationEntities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultationEntity }
     * 
     * 
     */
    public List<ConsultationEntity> getConsultationEntities() {
        if (consultationEntities == null) {
            consultationEntities = new ArrayList<ConsultationEntity>();
        }
        return this.consultationEntities;
    }

    /**
     * Gets the value of the doctorId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the value of the doctorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDoctorId(Long value) {
        this.doctorId = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the qualifications property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifications() {
        return qualifications;
    }

    /**
     * Sets the value of the qualifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifications(String value) {
        this.qualifications = value;
    }

}
