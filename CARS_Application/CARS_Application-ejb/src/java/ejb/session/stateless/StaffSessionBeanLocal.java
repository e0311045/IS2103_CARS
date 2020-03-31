/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;


public interface StaffSessionBeanLocal {
    public Long createStaffEntity(StaffEntity newStaffEntity);

    public StaffEntity retrieveStaffbyUserName(String userName);

    public StaffEntity retrieveStaffbyUserName(Long staffId);

    public void updateStaff(StaffEntity staffEntity);

    public void deleteStaff(Long staffId);
}
