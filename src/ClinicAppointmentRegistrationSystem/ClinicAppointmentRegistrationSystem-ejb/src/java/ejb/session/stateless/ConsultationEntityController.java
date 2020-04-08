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
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(ConsultationEntityControllerLocal.class)
@Remote(ConsultationEntityControllerRemote.class)

public class ConsultationEntityController implements ConsultationEntityControllerLocal, ConsultationEntityControllerRemote {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;

    public ConsultationEntityController() {
    }

    @Override
    public Long createConsultation(ConsultationEntity newConsultationEntity, String identityNumber, Long doctorId) {
        em.persist(newConsultationEntity);
        em.flush();
        
        return newConsultationEntity.getConsultationId();
//        PatientEntity patient = em.find(PatientEntity.class, identityNumber);
//
//        if (patient != null) {
//            newConsultationEntity.setPatient(patient);
//            patient.addConsultation(newConsultationEntity);
////            try {
////                
////            } catch (PatientAddConsultationException ex) {
////                Logger.getLogger(ConsultationEntityController.class.getName()).log(Level.SEVERE, null, ex);
////            }
//        }
//
//        DoctorEntity doctor = em.find(DoctorEntity.class, doctorId);
//
//        if (doctor != null) {
//            newConsultationEntity.setDoctor(doctor);
//            doctor.addConsultation(newConsultationEntity);
//        }
////            try {
////                
////            } catch (DoctorAddConsultationException ex) {
////                Logger.getLogger(ConsultationEntityController.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        }
//        em.flush();
    }

    @Override
    public List<ConsultationEntity> retrieveAllConsultations() {
        Query query = em.createQuery("SELECT c FROM ConsultationEntity c");
        return query.getResultList();
    }
    
}
