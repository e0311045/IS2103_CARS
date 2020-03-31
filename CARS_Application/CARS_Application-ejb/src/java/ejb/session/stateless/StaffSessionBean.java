
package ejb.session.stateless;

import entity.StaffEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(StaffSessionBeanLocal.class)
@Remote(StaffSessionBeanRemote.class)
public class StaffSessionBean implements StaffSessionBeanRemote, StaffSessionBeanLocal 
{
    @PersistenceContext(unitName = "CARS_Application-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createStaffEntity(StaffEntity newStaffEntity)
    {
        em.persist(newStaffEntity);
        em.flush(); //Ensure insert statement is executed
        
        return newStaffEntity.getStaffId();
    }
    
    @Override
    public StaffEntity retrieveStaffbyUserName(String userName)
    {
        StaffEntity staffEntity = em.find(StaffEntity.class, userName);
        
        return staffEntity;
    }
    
    @Override
    public StaffEntity retrieveStaffbyUserName(Long staffId)
    {
        StaffEntity staffEntity = em.find(StaffEntity.class, staffId);
        
        return staffEntity;
    }
    
    @Override
    public void updateStaff(StaffEntity staffEntity)
    {
        em.merge(staffEntity);
    }
    
    @Override
    public void deleteStaff(Long staffId)
    {
        StaffEntity staffEntity = retrieveStaffbyUserName(staffId);
        em.remove(staffEntity);
    }

}
