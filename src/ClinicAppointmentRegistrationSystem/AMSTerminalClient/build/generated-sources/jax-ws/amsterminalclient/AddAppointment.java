
package amsterminalclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addAppointment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addAppointment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identityNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="securityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="doctorId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="inputDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="inputTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addAppointment", propOrder = {
    "identityNumber",
    "securityCode",
    "doctorId",
    "inputDate",
    "inputTime"
})
public class AddAppointment {

    protected String identityNumber;
    protected String securityCode;
    protected long doctorId;
    protected String inputDate;
    protected String inputTime;

    /**
     * Gets the value of the identityNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentityNumber() {
        return identityNumber;
    }

    /**
     * Sets the value of the identityNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentityNumber(String value) {
        this.identityNumber = value;
    }

    /**
     * Gets the value of the securityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Sets the value of the securityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityCode(String value) {
        this.securityCode = value;
    }

    /**
     * Gets the value of the doctorId property.
     * 
     */
    public long getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the value of the doctorId property.
     * 
     */
    public void setDoctorId(long value) {
        this.doctorId = value;
    }

    /**
     * Gets the value of the inputDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputDate() {
        return inputDate;
    }

    /**
     * Sets the value of the inputDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputDate(String value) {
        this.inputDate = value;
    }

    /**
     * Gets the value of the inputTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputTime() {
        return inputTime;
    }

    /**
     * Sets the value of the inputTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputTime(String value) {
        this.inputTime = value;
    }

}
