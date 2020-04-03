/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package amsterminalclient;

import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import java.text.ParseException;
import javax.ejb.EJB;

public class Main {

    @EJB
    private static StaffEntityControllerRemote staffEntityControllerRemote;

    @EJB
    private static DoctorEntityControllerRemote doctorEntityControllerRemote;

    @EJB
    private static PatientEntityControllerRemote patientEntityControllerRemote;

    @EJB
    private static AppointmentEntityControllerRemote appointmentEntityControllerRemote;

    public static void main(String[] args) throws ParseException {

        MainApp mainApp = new MainApp(staffEntityControllerRemote, doctorEntityControllerRemote, patientEntityControllerRemote, appointmentEntityControllerRemote);
        mainApp.runApp();
    }
}
