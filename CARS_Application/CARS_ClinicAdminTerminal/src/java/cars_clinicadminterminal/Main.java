
package cars_clinicadminterminal;

import javax.ejb.EJB;
import ejb.session.stateless.StaffEntitySessionBeanRemote;

public class Main {

    @EJB
    private static StaffEntitySessionBeanRemote staffSessionBean;


    public static void main(String[] args) 
    {
        MainApp mainApp = new MainApp(staffSessionBean);
        mainApp.runApp();
    }
    
}
