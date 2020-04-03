
package amsterminalclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the amsterminalclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InvalidLoginException_QNAME = new QName("http://ws.session.ejb/", "InvalidLoginException");
    private final static QName _AddAppointment_QNAME = new QName("http://ws.session.ejb/", "addAppointment");
    private final static QName _AddAppointmentResponse_QNAME = new QName("http://ws.session.ejb/", "addAppointmentResponse");
    private final static QName _CancelAppointment_QNAME = new QName("http://ws.session.ejb/", "cancelAppointment");
    private final static QName _CancelAppointmentResponse_QNAME = new QName("http://ws.session.ejb/", "cancelAppointmentResponse");
    private final static QName _DoLogin_QNAME = new QName("http://ws.session.ejb/", "doLogin");
    private final static QName _DoLoginResponse_QNAME = new QName("http://ws.session.ejb/", "doLoginResponse");
    private final static QName _RegisterPatient_QNAME = new QName("http://ws.session.ejb/", "registerPatient");
    private final static QName _RegisterPatientResponse_QNAME = new QName("http://ws.session.ejb/", "registerPatientResponse");
    private final static QName _ViewAppointments_QNAME = new QName("http://ws.session.ejb/", "viewAppointments");
    private final static QName _ViewAppointmentsResponse_QNAME = new QName("http://ws.session.ejb/", "viewAppointmentsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: amsterminalclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidLoginException }
     * 
     */
    public InvalidLoginException createInvalidLoginException() {
        return new InvalidLoginException();
    }

    /**
     * Create an instance of {@link AddAppointment }
     * 
     */
    public AddAppointment createAddAppointment() {
        return new AddAppointment();
    }

    /**
     * Create an instance of {@link AddAppointmentResponse }
     * 
     */
    public AddAppointmentResponse createAddAppointmentResponse() {
        return new AddAppointmentResponse();
    }

    /**
     * Create an instance of {@link CancelAppointment }
     * 
     */
    public CancelAppointment createCancelAppointment() {
        return new CancelAppointment();
    }

    /**
     * Create an instance of {@link CancelAppointmentResponse }
     * 
     */
    public CancelAppointmentResponse createCancelAppointmentResponse() {
        return new CancelAppointmentResponse();
    }

    /**
     * Create an instance of {@link DoLogin }
     * 
     */
    public DoLogin createDoLogin() {
        return new DoLogin();
    }

    /**
     * Create an instance of {@link DoLoginResponse }
     * 
     */
    public DoLoginResponse createDoLoginResponse() {
        return new DoLoginResponse();
    }

    /**
     * Create an instance of {@link RegisterPatient }
     * 
     */
    public RegisterPatient createRegisterPatient() {
        return new RegisterPatient();
    }

    /**
     * Create an instance of {@link RegisterPatientResponse }
     * 
     */
    public RegisterPatientResponse createRegisterPatientResponse() {
        return new RegisterPatientResponse();
    }

    /**
     * Create an instance of {@link ViewAppointments }
     * 
     */
    public ViewAppointments createViewAppointments() {
        return new ViewAppointments();
    }

    /**
     * Create an instance of {@link ViewAppointmentsResponse }
     * 
     */
    public ViewAppointmentsResponse createViewAppointmentsResponse() {
        return new ViewAppointmentsResponse();
    }

    /**
     * Create an instance of {@link AppointmentEntity }
     * 
     */
    public AppointmentEntity createAppointmentEntity() {
        return new AppointmentEntity();
    }

    /**
     * Create an instance of {@link DoctorEntity }
     * 
     */
    public DoctorEntity createDoctorEntity() {
        return new DoctorEntity();
    }

    /**
     * Create an instance of {@link ConsultationEntity }
     * 
     */
    public ConsultationEntity createConsultationEntity() {
        return new ConsultationEntity();
    }

    /**
     * Create an instance of {@link PatientEntity }
     * 
     */
    public PatientEntity createPatientEntity() {
        return new PatientEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InvalidLoginException }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InvalidLoginException")
    public JAXBElement<InvalidLoginException> createInvalidLoginException(InvalidLoginException value) {
        return new JAXBElement<InvalidLoginException>(_InvalidLoginException_QNAME, InvalidLoginException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAppointment }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddAppointment }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "addAppointment")
    public JAXBElement<AddAppointment> createAddAppointment(AddAppointment value) {
        return new JAXBElement<AddAppointment>(_AddAppointment_QNAME, AddAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAppointmentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddAppointmentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "addAppointmentResponse")
    public JAXBElement<AddAppointmentResponse> createAddAppointmentResponse(AddAppointmentResponse value) {
        return new JAXBElement<AddAppointmentResponse>(_AddAppointmentResponse_QNAME, AddAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelAppointment }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CancelAppointment }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "cancelAppointment")
    public JAXBElement<CancelAppointment> createCancelAppointment(CancelAppointment value) {
        return new JAXBElement<CancelAppointment>(_CancelAppointment_QNAME, CancelAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelAppointmentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CancelAppointmentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "cancelAppointmentResponse")
    public JAXBElement<CancelAppointmentResponse> createCancelAppointmentResponse(CancelAppointmentResponse value) {
        return new JAXBElement<CancelAppointmentResponse>(_CancelAppointmentResponse_QNAME, CancelAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoLogin }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DoLogin }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "doLogin")
    public JAXBElement<DoLogin> createDoLogin(DoLogin value) {
        return new JAXBElement<DoLogin>(_DoLogin_QNAME, DoLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoLoginResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DoLoginResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "doLoginResponse")
    public JAXBElement<DoLoginResponse> createDoLoginResponse(DoLoginResponse value) {
        return new JAXBElement<DoLoginResponse>(_DoLoginResponse_QNAME, DoLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterPatient }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegisterPatient }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "registerPatient")
    public JAXBElement<RegisterPatient> createRegisterPatient(RegisterPatient value) {
        return new JAXBElement<RegisterPatient>(_RegisterPatient_QNAME, RegisterPatient.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterPatientResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegisterPatientResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "registerPatientResponse")
    public JAXBElement<RegisterPatientResponse> createRegisterPatientResponse(RegisterPatientResponse value) {
        return new JAXBElement<RegisterPatientResponse>(_RegisterPatientResponse_QNAME, RegisterPatientResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAppointments }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ViewAppointments }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAppointments")
    public JAXBElement<ViewAppointments> createViewAppointments(ViewAppointments value) {
        return new JAXBElement<ViewAppointments>(_ViewAppointments_QNAME, ViewAppointments.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewAppointmentsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ViewAppointmentsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewAppointmentsResponse")
    public JAXBElement<ViewAppointmentsResponse> createViewAppointmentsResponse(ViewAppointmentsResponse value) {
        return new JAXBElement<ViewAppointmentsResponse>(_ViewAppointmentsResponse_QNAME, ViewAppointmentsResponse.class, null, value);
    }

}
