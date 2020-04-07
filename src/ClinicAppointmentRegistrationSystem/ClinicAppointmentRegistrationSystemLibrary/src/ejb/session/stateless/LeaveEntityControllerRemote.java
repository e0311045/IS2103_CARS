/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LeaveEntity;
import java.util.List;
import util.exception.LeaveApplicationException;
import util.exception.LeaveDeniedException;
import util.exception.LeaveNotFoundException;
import util.exception.MaximumLeaveAppliedException;


public interface LeaveEntityControllerRemote {
    public LeaveEntity createNewLeave(LeaveEntity newLeaveEntity) throws MaximumLeaveAppliedException, LeaveApplicationException, LeaveDeniedException;

    public List<LeaveEntity> retrieveAllLeaveByDoctor(Long doctorId) throws LeaveNotFoundException;
    
    public LeaveEntity retrieveLeaveByDoctorWeek(Long doctorId, int weekNo);
}
