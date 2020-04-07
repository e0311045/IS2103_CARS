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
    public void createConsultation(ConsultationEntity newConsultationEntity, String identityNumber, Long doctorId)
    {
        em.persist(newConsultationEntity);
        em.flush();      
    }

    @Override
    public List<ConsultationEntity> retrieveAllConsultations() {
        Query query = em.createQuery("SELECT c FROM ConsultationEntity c");
        return query.getResultList();
    }
    
}
