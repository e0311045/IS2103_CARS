/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LeaveEntity;
import java.sql.Date;
import javax.ejb.Stateless;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.LeaveDeniedException;
import util.exception.NoLeaveRecordFoundException;



@Local(LeaveEntitySessionBeanLocal.class)
@Remote(LeaveEntitySessionBeanRemote.class)
@Stateless
public class LeaveEntitySessionBean implements LeaveEntitySessionBeanRemote, LeaveEntitySessionBeanLocal 
{

    @PersistenceContext(unitName = "CARS_Application-ejbPU")
    private EntityManager em;

    @Override
    public Long createLeave(LeaveEntity newLeaveEntity) throws LeaveDeniedException
    {
        em.persist(newLeaveEntity);
        em.flush(); //Ensure insert statement is executed
        
        return newLeaveEntity.getLeaveId();
    }
    
    @Override
    public LeaveEntity retrieveLeaveByDoctor(Long doctorId)
    {
        LeaveEntity leaveEntity = em.find(LeaveEntity.class, doctorId);
        
        return leaveEntity;
    }
    
    @Override
    public LeaveEntity retrieveLeaveByDate(String dateStr) throws NoLeaveRecordFoundException
    {
        Date date = Date.valueOf(dateStr);
        Query query = em.createQuery("SELECT le FROM LeaveEntity le WHERE le.dateOnLeave = :inDateOnLeave");
        query.setParameter("inDateOnLeave", date);
        
        try {
            LeaveEntity leaveRecord = (LeaveEntity) query.getSingleResult();
            return leaveRecord;

        } catch (NoResultException ex) {
            throw new NoLeaveRecordFoundException(dateStr + " does not have any leave applied!");
        }
    }
    
    
}
