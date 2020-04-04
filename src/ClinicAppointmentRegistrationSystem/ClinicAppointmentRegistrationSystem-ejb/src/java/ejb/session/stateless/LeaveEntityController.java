/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LeaveEntity;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.date.DateHelper;
import util.exception.LeaveApplicationException;
import util.exception.LeaveDeniedException;
import util.exception.LeaveNotFoundException;
import util.exception.MaximumLeaveAppliedException;

@Remote(LeaveEntityControllerRemote.class)
@Local(LeaveEntityControllerLocal.class)
@Stateless
public class LeaveEntityController implements LeaveEntityControllerRemote, LeaveEntityControllerLocal {

    @EJB
    private AppointmentEntityControllerLocal appointmentEntityController;
    
    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public LeaveEntity createNewLeave(LeaveEntity newLeaveEntity) throws MaximumLeaveAppliedException, LeaveApplicationException, LeaveDeniedException
    {                      
        Date currDate = new Date();
        Date leaveDate = newLeaveEntity.getLeaveDate();
        DateHelper.dateSDF.format(currDate);
        if(appointmentEntityController.retrieveAppointmentByDoctorDate(newLeaveEntity.getLeaveDoctor().getDoctorId(), leaveDate)!=null){
            throw new LeaveDeniedException("Leave has been denied as " + newLeaveEntity.getLeaveDoctor().getDoctorId() + " has an appointment on " + leaveDate.toString());
        }
        else if(DateHelper.dateDiff(currDate,leaveDate )<7){
            throw new LeaveApplicationException("Leave has to be applied in a week advance!");
        }
        else if(retrieveLeaveByDoctorWeek(newLeaveEntity.getLeaveDoctor().getDoctorId(),newLeaveEntity.getWeekNo())!=null) {
            throw new MaximumLeaveAppliedException("Maximum Leave Applied for given week " + newLeaveEntity.getWeekNo() + " as leave have been applied on " + newLeaveEntity.getLeaveDate());
        }
        else{
            em.persist(newLeaveEntity);
            newLeaveEntity.getLeaveDoctor().addLeave(newLeaveEntity);
            em.flush();
            return newLeaveEntity;
        }
  
    }
    
    @Override
    public List<LeaveEntity> retrieveAllLeaveByDoctor(Long doctorId) throws LeaveNotFoundException {
        Query query = em.createQuery("SELECT l FROM LeaveEntity l WHERE l.onLeaveDoctor.doctorId = :inDoctorId");
        query.setParameter("inDoctorId", doctorId);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new LeaveNotFoundException("Leave does not exist!");
        }
    }
    
    @Override
    public LeaveEntity retrieveLeaveByDoctorWeek(Long doctorId, int weekNo) 
    {
        Query query = em.createQuery("SELECT l FROM LeaveEntity l WHERE l.onLeaveDoctor.doctorId = :inDoctorId AND l.weekNo = :inWeekNo");
        query.setParameter("inDoctorId", doctorId);
        query.setParameter("inWeekNo", weekNo);
        
        return (LeaveEntity) query.getSingleResult();
    }
}
