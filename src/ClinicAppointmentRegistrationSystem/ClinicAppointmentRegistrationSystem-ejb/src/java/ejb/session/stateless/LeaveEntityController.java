/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.LeaveEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.date.DateHelper;
import util.exception.LeaveApplicationException;
import util.exception.LeaveExistException;
import util.exception.UnknownPersistenceException;

@Remote(LeaveEntityControllerRemote.class)
@Local(LeaveEntityControllerLocal.class)
@Stateless
public class LeaveEntityController implements LeaveEntityControllerRemote, LeaveEntityControllerLocal {

    @EJB
    private AppointmentEntityControllerLocal appointmentEntityController;
    
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;
    
    
    @Override
    public Long createNewLeave(LeaveEntity newLeaveEntity) throws UnknownPersistenceException, LeaveApplicationException , LeaveExistException
    {                      
        Date currDate = new Date();
        
        if(DateHelper.dateDiff(currDate, newLeaveEntity.getLeaveDate()) < 7){
            throw new LeaveApplicationException("Minimum days to apply for leave in advance is 2 from current date " + currDate.toString());
        }
        
        AppointmentEntity appointment = appointmentEntityController.retrieveAppointmentByDoctorDate(newLeaveEntity.getLeaveDoctor().getDoctorId(), newLeaveEntity.getLeaveDate());
        if(appointment!=null){
            throw new LeaveApplicationException("Doctor has an appointment on " + newLeaveEntity.getLeaveDate().toString());
        }
        LeaveEntity leaveEntity = retrieveLeaveByDoctorWeek(newLeaveEntity.getLeaveDoctor().getDoctorId(),DateHelper.getWeekNo(newLeaveEntity.getLeaveDate()));
        if(leaveEntity!=null){
            throw new LeaveApplicationException("Doctor has already applied leave on " + newLeaveEntity.getLeaveDate().toString());
        }
       
        try{
            em.persist(newLeaveEntity);          
            em.flush();          
            return newLeaveEntity.getLeaveId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new LeaveExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public List<LeaveEntity> retrieveAllLeaveByDoctor(Long doctorId) {
        Query query = em.createQuery("SELECT l FROM LeaveEntity l WHERE l.leaveDoctor.doctorId = :inDoctorId");
        query.setParameter("inDoctorId", doctorId);

        return query.getResultList();      
    }
    
    @Override
    public LeaveEntity retrieveLeaveByDoctorWeek(Long doctorId, int weekNo)
    {
        Query query = em.createQuery("SELECT l FROM LeaveEntity l WHERE l.onLeaveDoctor.doctorId = :inDoctorId AND l.weekNo = :inWeekNo");
        query.setParameter("inDoctorId", doctorId);
        query.setParameter("inWeekNo", weekNo);
        
        return (LeaveEntity) query.getSingleResult();
    }
    
    @Override
    public LeaveEntity retrieveLeaveById(Long id)
    {
        LeaveEntity leaveEntity = em.find(LeaveEntity.class, id);
        
        return leaveEntity;     
    }
}
