
package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialsException;
import util.exception.StaffNotFoundException;

@Stateless
@Local(StaffEntitySessionBeanLocal.class)
@Remote(StaffEntitySessionBeanRemote.class)
public class StaffEntitySessionBean implements StaffEntitySessionBeanRemote, StaffEntitySessionBeanLocal 
{
    @PersistenceContext(unitName = "CARS_Application-ejbPU")
    private EntityManager em;
    
    @Override
    public Long registerStaff(StaffEntity newStaffEntity)
    {
        em.persist(newStaffEntity);
        em.flush(); //Ensure insert statement is executed
        
        return newStaffEntity.getStaffId();
    }
    
    @Override
    public List<StaffEntity> retrieveAllStaff()
    {
        Query query = em.createQuery("SELECT staff FROM StaffEntity staff");
        
        return query.getResultList();
    }
    
    @Override
    public StaffEntity retrieveStaffDetails(Long staffId) throws StaffNotFoundException
    {
        
        StaffEntity staffEntity = em.find(StaffEntity.class, staffId);
        
        return staffEntity;
    }
    
    @Override
    public StaffEntity retrieveStaffbyUserName(String userName) throws StaffNotFoundException
    {
        Query query = em.createQuery("SELECT se FROM StaffEntity se WHERE se.userName = :inUsername");
        query.setParameter("inUsername", userName);
        try {
            StaffEntity staff = (StaffEntity) query.getSingleResult();
            return staff;

        } catch (NoResultException ex) {
            throw new StaffNotFoundException("Staff username " + userName + " does not exist!");
        }
    }
    

    @Override
    public void updateStaff(StaffEntity staffEntity)
    {
        em.merge(staffEntity);
    }
    
    @Override
    public void deleteStaff(Long staffId) throws StaffNotFoundException
    {  
        StaffEntity staffEntity = retrieveStaffDetails(staffId);
        em.remove(staffEntity);  
    }
    
        
    @Override
    public StaffEntity staffLogin(String userName, String password) throws InvalidLoginCredentialsException 
    {
        try {
            StaffEntity currentStaffEntity = retrieveStaffbyUserName(userName);

            if (currentStaffEntity.getPassword().equals(password)) {
                return currentStaffEntity;
            } else {
                throw new InvalidLoginCredentialsException("Staff does not exist or invalid password!");
            }
        } catch (StaffNotFoundException ex) {
            throw new InvalidLoginCredentialsException("Username does not exist or invalid password!");
        }
    }

}
