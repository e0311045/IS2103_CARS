/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateless;

import entity.ConsultationEntity;
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
import util.exception.DoctorAddConsultationException;
import util.exception.PatientAddConsultationException;

@Stateless
@Local(ConsultationEntityControllerLocal.class)
@Remote(ConsultationEntityControllerRemote.class)

public class ConsultationEntityController implements ConsultationEntityControllerLocal, ConsultationEntityControllerRemote {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager entityManager;

    public ConsultationEntityController() {
    }

    @Override
    public void createConsultation(ConsultationEntity newConsultationEntity, String identityNumber, Long doctorId)// throws PatientAddConsultationException
    {
        entityManager.persist(newConsultationEntity);
        PatientEntity patient = entityManager.find(PatientEntity.class, identityNumber);

        if (patient != null) {
            newConsultationEntity.setPatient(patient);
            try {
                patient.addConsultation(newConsultationEntity);
            } catch (PatientAddConsultationException ex) {
                Logger.getLogger(ConsultationEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (doctor != null) {
            newConsultationEntity.setDoctor(doctor);
            try {
                doctor.addConsultation(newConsultationEntity);
            } catch (DoctorAddConsultationException ex) {
                Logger.getLogger(ConsultationEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<ConsultationEntity> retrieveAllConsultations() {
        Query query = entityManager.createQuery("SELECT c FROM ConsultationEntity c");
        return query.getResultList();
    }
}
