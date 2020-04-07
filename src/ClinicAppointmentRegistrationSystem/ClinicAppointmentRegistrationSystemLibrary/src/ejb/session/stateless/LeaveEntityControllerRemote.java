/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LeaveEntity;
import java.util.List;
import util.exception.LeaveApplicationException;
import util.exception.LeaveExistException;
import util.exception.UnknownPersistenceException;


public interface LeaveEntityControllerRemote {
    public Long createNewLeave(LeaveEntity newLeaveEntity) throws UnknownPersistenceException, LeaveApplicationException , LeaveExistException;

    public List<LeaveEntity> retrieveAllLeaveByDoctor(Long doctorId);
    
    public LeaveEntity retrieveLeaveByDoctorWeek(Long doctorId, int weekNo);

    public LeaveEntity retrieveLeaveById(Long id);
}
