/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.PatientNotFoundException;

@Stateless
@Local(PatientEntitySessionBeanLocal.class)
@Remote(PatientEntitySessionBeanRemote.class)
public class PatientEntitySessionBean implements PatientEntitySessionBeanRemote, PatientEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CARS_Application-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createPatient(PatientEntity patientEntity)
    {
        em.persist(patientEntity);
        em.flush();
        
        return patientEntity.getPatientId();
    }
    
    @Override
    public List<PatientEntity> retrieveAllPatient()
    {
        Query query = em.createQuery("SELECT pe FROM PatientEntity pe");
        List<PatientEntity> patients = query.getResultList();
        
        return patients;      
    }
    
    @Override
    public PatientEntity retrievePatientById(Long patientId) throws PatientNotFoundException
    {
        PatientEntity patient = em.find(PatientEntity.class, patientId);
        
        return patient;
    }
    
    @Override
    public PatientEntity retrievePatientByIdentity(String identityNo) throws PatientNotFoundException
    {
        Query query = em.createQuery("SELECT pe FROM PatientEntity pe WHERE pe.identityNo = :inIdentityNo");
        query.setParameter("inIdentityNo", identityNo);
        try {
            PatientEntity patient = (PatientEntity) query.getSingleResult();
            return patient;

        } catch (NoResultException ex) {
            throw new PatientNotFoundException("Patient identityNo " + identityNo + " does not exist!");
        }
    }
    
    @Override
    public void updatePatient(PatientEntity patientEntity)
    {
        em.merge(patientEntity);
    }
    
    @Override
    public void deletePatient(Long patientId) throws PatientNotFoundException
    {
        PatientEntity patient = retrievePatientById(patientId);
        em.remove(patient);   
    }
}
