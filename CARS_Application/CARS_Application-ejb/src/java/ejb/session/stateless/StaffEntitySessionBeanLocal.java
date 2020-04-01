/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import util.exception.InvalidLoginCredentialsException;
import util.exception.StaffNotFoundException;


public interface StaffEntitySessionBeanLocal {

    public Long registerStaff(StaffEntity newStaffEntity);

    public List<StaffEntity> retrieveAllStaff();

    public StaffEntity retrieveStaffDetails(Long staffId) throws StaffNotFoundException;

    public StaffEntity retrieveStaffbyUserName(String userName) throws StaffNotFoundException;

    public void updateStaff(StaffEntity staffEntity);

    public StaffEntity staffLogin(String userName, String password) throws InvalidLoginCredentialsException;

    public void deleteStaff(Long staffId) throws StaffNotFoundException;

}
