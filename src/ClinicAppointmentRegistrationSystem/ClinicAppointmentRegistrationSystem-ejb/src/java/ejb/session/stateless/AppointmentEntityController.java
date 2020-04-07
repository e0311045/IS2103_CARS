/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreateAppointmentException;
import util.exception.AppointmentNotFoundException;
import util.exception.DoctorNotFoundException;
import util.exception.PatientNotFoundException;
import util.exception.UnknownPersistenceException;

@Stateless
@Local(AppointmentEntityControllerLocal.class)
@Remote(AppointmentEntityControllerRemote.class)

public class AppointmentEntityController implements AppointmentEntityControllerLocal, AppointmentEntityControllerRemote {

    @EJB
    private LeaveEntityControllerLocal leaveEntityController;

    @EJB
    private DoctorEntityControllerLocal doctorEntityControllerLocal;

    @EJB
    private PatientEntityControllerLocal patientEntityControllerLocal;

    
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;

    public AppointmentEntityController() {
    }
    
    @Override
    public Long createAppointment(AppointmentEntity newAppointmentEntity) {
        em.persist(newAppointmentEntity);
        em.flush();
        
        return newAppointmentEntity.getAppointmentId();
    }
    
    @Override
    public Long createAppointment(AppointmentEntity newAppointmentEntity, String identityNumber, Long doctorId) throws CreateAppointmentException, UnknownPersistenceException {
        leaveEntityController.retrieveAllLeaveByDoctor(doctorId);
        
        try {
            PatientEntity registeredPatient = patientEntityControllerLocal.retrievePatientByIdentityNumber(identityNumber);
            DoctorEntity doctor = doctorEntityControllerLocal.retrieveDoctorById(doctorId);
            newAppointmentEntity.setPatient(registeredPatient);
            newAppointmentEntity.setDoctor(doctor);
            
            em.persist(newAppointmentEntity);
            em.flush();

            return newAppointmentEntity.getAppointmentId();
        } catch (DoctorNotFoundException | PatientNotFoundException ex) {
            throw new CreateAppointmentException(ex.getMessage());
        }
    }

    @Override
    public List<AppointmentEntity> retrieveAllAppointments() {
        Query query = em.createQuery("SELECT a FROM AppointmentEntity a");
        return query.getResultList();
    }

    @Override
    public AppointmentEntity retrieveAppointmentByAppointmentId(Long appointmentId) throws AppointmentNotFoundException {
        AppointmentEntity appointmentEntity = em.find(AppointmentEntity.class, appointmentId);

        if (appointmentEntity != null) {
            return appointmentEntity;
        } else {
            throw new AppointmentNotFoundException("Appointment ID " + appointmentId + " does not exist!");
        }
    }
    
    @Override
    public AppointmentEntity retrieveAppointmentByDoctorDate(Long doctorId, Date date) {
        Query query = em.createQuery("SELECT appt FROM AppointmentEntity appt WHERE appt.appointmentDoctor.doctorId = :inDoctorId AND appt.appointmentDate = :inDate");
        query.setParameter("inDoctorId", doctorId);
        query.setParameter("inDate", date);
        
        
        AppointmentEntity appointmentEntity = (AppointmentEntity) query.getSingleResult();
        
        return appointmentEntity;
    }
    
        @Override
    public void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException {

        AppointmentEntity appointmentEntity = retrieveAppointmentByAppointmentId(appointmentId);
        em.remove(appointmentEntity);
        //appointmentEntities.setDoctorEntity(null);

        PatientEntity patientEntity = em.find(PatientEntity.class, appointmentEntity.getPatient().getIdentityNumber());
        patientEntity.getAppointmentEntities().remove(appointmentEntity);

        DoctorEntity doctorEntity = em.find(DoctorEntity.class, appointmentEntity.getDoctor().getDoctorId());
        doctorEntity.getAppointmentEntities().remove(appointmentEntity);

    }

}
