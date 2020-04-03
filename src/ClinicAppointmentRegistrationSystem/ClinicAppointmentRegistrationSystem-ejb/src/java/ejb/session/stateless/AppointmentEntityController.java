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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AppointmentNotFoundException;
import util.exception.DoctorAddAppointmentException;
import util.exception.PatientAddAppointmentException;

@Stateless
@Local(AppointmentEntityControllerLocal.class)
@Remote(AppointmentEntityControllerRemote.class)

public class AppointmentEntityController implements AppointmentEntityControllerLocal, AppointmentEntityControllerRemote {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager entityManager;

    public AppointmentEntityController() {
    }

    @Override
    public void createAppointment(AppointmentEntity newAppointmentEntity, String identityNumber, Long doctorId) {
        entityManager.persist(newAppointmentEntity);
        PatientEntity patient = entityManager.find(PatientEntity.class, identityNumber);

        if (patient != null) {
            newAppointmentEntity.setPatient(patient);
            try {
                patient.addAppointment(newAppointmentEntity);
            } catch (PatientAddAppointmentException ex) {
                Logger.getLogger(AppointmentEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (doctor != null) {
            newAppointmentEntity.setDoctor(doctor);
            try {
                doctor.addAppointment(newAppointmentEntity);
            } catch (DoctorAddAppointmentException ex) {
                Logger.getLogger(AppointmentEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<AppointmentEntity> retrieveAllAppointments() {
        Query query = entityManager.createQuery("SELECT a FROM AppointmentEntity a");
        return query.getResultList();
    }

    @Override
    public void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException {

        AppointmentEntity appointmentEntity = retrieveAppointmentByAppointmentId(appointmentId);
        entityManager.remove(appointmentEntity);
        //appointmentEntities.setDoctorEntity(null);

        PatientEntity patientEntity = entityManager.find(PatientEntity.class, appointmentEntity.getPatient().getIdentityNumber());
        patientEntity.getAppointmentEntities().remove(appointmentEntity);

        DoctorEntity doctorEntity = entityManager.find(DoctorEntity.class, appointmentEntity.getDoctor().getDoctorId());
        doctorEntity.getAppointmentEntities().remove(appointmentEntity);

    }

    @Override
    public AppointmentEntity retrieveAppointmentByAppointmentId(Long appointmentId) throws AppointmentNotFoundException {
        AppointmentEntity appointmentEntity = entityManager.find(AppointmentEntity.class, appointmentId);

        if (appointmentEntity != null) {
            return appointmentEntity;
        } else {
            throw new AppointmentNotFoundException("Appointment ID " + appointmentId + " does not exist!");
        }
    }
}
