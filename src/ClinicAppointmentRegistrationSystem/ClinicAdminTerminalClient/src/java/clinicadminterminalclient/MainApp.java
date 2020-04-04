/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package clinicadminterminalclient;

import ejb.session.stateful.RegistrationControllerRemote;
import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.ConsultationEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.LeaveEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.StaffEntity;
import java.text.ParseException;
import java.util.Scanner;
import util.exception.AppointmentNotFoundException;
import util.exception.InvalidLoginException;

public class MainApp {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private RegistrationControllerRemote registrationControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    private LeaveEntityControllerRemote leaveEntityControllerRemote;

    private StaffEntity currentStaffEntity;
    private RegistrationModule registrationModule;
    private AppointmentModule appointmentModule;
    private AdministrationModule administrationModule;
    public int queue = 0;

    public MainApp() {
    }

    MainApp(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote,
            PatientEntityControllerRemote patientEntityControllerRemote, RegistrationControllerRemote registrationControllerRemote, 
            ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote,
            LeaveEntityControllerRemote leaveEntityControllerRemote) 
    {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.registrationControllerRemote = registrationControllerRemote;
        this.consultationEntityControllerRemote = consultationEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
        this.leaveEntityControllerRemote = leaveEntityControllerRemote;
    }

    public void runApp() throws ParseException, AppointmentNotFoundException {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Clinic Appointment Registration System (CARS) ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {

                    try {
                        doLogin();
                        registrationModule = new RegistrationModule(staffEntityControllerRemote, doctorEntityControllerRemote, patientEntityControllerRemote, registrationControllerRemote, consultationEntityControllerRemote, appointmentEntityControllerRemote);
                        appointmentModule = new AppointmentModule(staffEntityControllerRemote, doctorEntityControllerRemote, patientEntityControllerRemote, registrationControllerRemote, appointmentEntityControllerRemote);
                        administrationModule = new AdministrationModule(staffEntityControllerRemote, doctorEntityControllerRemote, patientEntityControllerRemote, registrationControllerRemote,leaveEntityControllerRemote);
                        menuMain();
                    } catch (InvalidLoginException ex) {
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** CARS :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            try {
                currentStaffEntity = staffEntityControllerRemote.staffLogin(username, password);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginException ex) {
                System.out.println("Invalid login: " + ex.getMessage() + "\n");

                throw new InvalidLoginException();

            }
        } else {
            System.out.println("Invalid login!");
        }
    }

    private void menuMain() throws ParseException, AppointmentNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Main ***\n");
            System.out.println("You are login as " + currentStaffEntity.getFirstName() + " " + currentStaffEntity.getLastName() + "\n");
            System.out.println("1: Registration Operation");
            System.out.println("2: Appointment Operation");
            System.out.println("3: Administration Operation");
            System.out.println("4: Logout\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    registrationModule.menuRegistration();
                } else if (response == 2) {
                    appointmentModule.menuAppointment();
                } else if (response == 3) {
                    administrationModule.menuAdministration();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 4) {
                break;
            }
        }
    }
}
