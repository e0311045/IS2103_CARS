/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LeaveEntity;
import java.util.Date;
import java.util.List;
import util.exception.LeaveAlreadyExistException;
import util.exception.LeaveApplicationException;
import util.exception.LeaveNotFoundException;
import util.exception.UnknownPersistenceException;


public interface LeaveEntityControllerLocal {
    public Long createNewLeave(LeaveEntity newLeaveEntity);
    
    public Long createNewLeave(LeaveEntity newLeaveEntity, Date currDate) throws LeaveApplicationException , LeaveAlreadyExistException;

    public List<LeaveEntity> retrieveAllLeaveByDoctor(Long doctorId) throws LeaveNotFoundException;

    public LeaveEntity retrieveLeaveByDoctorWeek(Long doctorId, int weekNo);
    
    public LeaveEntity retrieveLeaveById(Long id);
    
}
