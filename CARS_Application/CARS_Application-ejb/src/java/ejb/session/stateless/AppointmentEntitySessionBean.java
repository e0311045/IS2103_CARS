
package ejb.session.stateless;

import entity.AppointmentEntity;
import java.sql.Date;
import java.util.List;
import util.date.DateHelper;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AppointmentNotFoundException;
import util.exception.PatientNotFoundException;


@Remote(AppointmentEntitySessionBeanRemote.class)
@Local(AppointmentEntitySessionBeanLocal.class)
@Stateless
public class AppointmentEntitySessionBean implements AppointmentEntitySessionBeanRemote, AppointmentEntitySessionBeanLocal 
{

    @EJB
    private PatientEntitySessionBeanLocal patientEntitySessionBean;

    @EJB
    private DoctorEntitySessionBeanLocal doctorEntitySessionBean;
    
    
    
    @PersistenceContext(unitName = "CARS_Application-ejbPU")
    private EntityManager em;

    public Long addAppointment(DoctorEntity d, String currentDate)
    {
        
    }
    
    public List<AppointmentEntity> retrieveAppointmentByPatientIdentity(String identityNo) throws AppointmentNotFoundException
    {
        Query query = em.createQuery("SELECT ae FROM AppointmentEntity ae JOIN ae.patient p WHERE p.identityNo = :inIdentityNo");
        query.setParameter("inIdentityNo", identityNo);
        
        try {
            List<AppointmentEntity> appointmentsByPatient = query.getResultList();           
            return appointmentsByPatient;
        } catch (NoResultException ex) {
            throw new AppointmentNotFoundException("Patient of " + identityNo + " does not have any appointments made!");
        }
    }
    
    public AppointmentEntity retrieveAppointmentById(Long apptId) throws AppointmentNotFoundException
    {
        Query query = em.createQuery("SELECT ae FROM AppointmentEntity ae WHERE ae.appointmentId = :inApptId");
        query.setParameter("inApptId", apptId);
        
        try {
            AppointmentEntity appointment = (AppointmentEntity) query.getSingleResult();           
            return appointment;
        } catch (NoResultException ex) {
            throw new AppointmentNotFoundException("Appointment ID" + apptId + " is invalid!");
        }
    }
    
    public List<AppointmentEntity> retrieveAppointmentByDoctorNDate(Long doctorId, Date date)
    {
        Query query = em.createQuery("SELECT ae FROM AppointmentEntity ae JOIN ae.doctor d WHERE ae.apptDate = :inDate AND d.doctorId = :inDoctorId");
        query.setParameter("inDate", date);
        query.setParameter("inDoctorId", doctorId);
        
        List<AppointmentEntity> appointments = query.getResultList();
        
        return appointments;
    }
    
    public List<String> showAvailability(String date)
    {
        
    }
    
    public void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException
    {
        AppointmentEntity appointment = retrieveAppointmentById(appointmentId);
        em.remove(appointment);
    }

}
