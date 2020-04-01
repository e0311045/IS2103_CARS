/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DoctorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.DoctorNotFoundException;

@Local(DoctorEntitySessionBeanLocal.class)
@Remote(DoctorEntitySessionBeanRemote.class)
@Stateless
public class DoctorEntitySessionBean implements DoctorEntitySessionBeanRemote, DoctorEntitySessionBeanLocal {

    @PersistenceContext(unitName = "CARS_Application-ejbPU")
    private EntityManager em;

    @Override
    public Long createDoctor(DoctorEntity doctor)
    {
        em.persist(doctor);
        em.flush();
        
        return doctor.getDoctorId();
    }
    
    @Override
    public List<DoctorEntity> retrieveAllDoctors()
    {
        Query query = em.createQuery("SELECT de FROM DoctorEntity de");
        
        return query.getResultList();
    }
    
    @Override
    public DoctorEntity retrieveDoctorById(Long doctorId) throws DoctorNotFoundException
    {
        DoctorEntity doctorEntity = em.find(DoctorEntity.class, doctorId);
        
        return doctorEntity;
    }
   
    
    @Override
    public void updateDoctor(DoctorEntity doctorEntity)
    {
        em.merge(doctorEntity);
    }
    
    @Override
    public void deleteDoctor(Long doctorId) throws DoctorNotFoundException
    {
        DoctorEntity doctorEntity = retrieveDoctorById(doctorId);
        em.remove(doctorEntity);
    }
}
