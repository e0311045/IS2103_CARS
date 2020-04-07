/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.InvalidLoginException;
import util.exception.StaffAlreadyExistException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;

@Stateless
@Local(StaffEntityControllerLocal.class)
@Remote(StaffEntityControllerRemote.class)

public class StaffEntityController implements StaffEntityControllerLocal, StaffEntityControllerRemote {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;

    public StaffEntityController() {
    }

    @Override
    public Long createNewStaff(StaffEntity newStaffEntity) {
//        try{
            em.persist(newStaffEntity);
            em.flush();

            return newStaffEntity.getStaffId();
//        } catch (PersistenceException ex) {
//            throw new StaffAlreadyExistException(ex.getMessage());
//        }
    }

    @Override
    public List<StaffEntity> retrieveAllStaffs() {
        
        Query query = em.createQuery("SELECT s FROM StaffEntity s");

        return query.getResultList();
    }

    @Override
    public StaffEntity retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException {
        StaffEntity staffEntity = em.find(StaffEntity.class, staffId);

        if (staffEntity != null) {
            return staffEntity;
        } else {
            throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }

    @Override
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT s FROM StaffEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (StaffEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }

    @Override
    public StaffEntity staffLogin(String username, String password) throws InvalidLoginException {
        try {
            StaffEntity staffEntity = retrieveStaffByUsername(username);

            if (staffEntity.getPassword().equals(password)) {
                return staffEntity;
            } else {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        } catch (StaffNotFoundException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateStaff(StaffEntity staffEntity) {
        em.merge(staffEntity);
    }

    @Override
    public void deleteStaff(Long staffId) throws StaffNotFoundException {
        StaffEntity staffEntityToRemove = retrieveStaffByStaffId(staffId);
        em.remove(staffEntityToRemove);
    }

}
