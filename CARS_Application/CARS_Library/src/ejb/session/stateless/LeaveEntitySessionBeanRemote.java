/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LeaveEntity;
import util.exception.LeaveDeniedException;
import util.exception.NoLeaveRecordFoundException;


public interface LeaveEntitySessionBeanRemote {

    public Long createLeave(LeaveEntity newLeaveEntity) throws LeaveDeniedException;

    public LeaveEntity retrieveLeaveByDoctor(Long doctorId);
    
    public LeaveEntity retrieveLeaveByDate(String dateStr) throws NoLeaveRecordFoundException;
}
