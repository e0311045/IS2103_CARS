
package cars_clinicadminterminal;

import entity.StaffEntity;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialsException;
import ejb.session.stateless.StaffEntitySessionBeanRemote;

public class MainApp {
    
    private StaffEntitySessionBeanRemote staffSessionBeanRemote;
    
    
    //Modules
    private AdministrationManagementModule administrationManagementModule;
    private RegistrationManagementModule registrationManagementModule;
    private AppointmentManagementModule appointmentManagementModule;
    //Entity Variables
    private StaffEntity currentStaffEntity;
    
    private static final String INVALID_OPTION = "Invalid option, please try again!\n";
    
    public MainApp(StaffEntitySessionBeanRemote staffSessionBeanRemote)
    {
        this.staffSessionBeanRemote = staffSessionBeanRemote;
    }
    
    public void runApp()
    {
        System.out.println("*** Welcome to Clinic Appointment Registration System (CARS) ***");
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Car Rental Management System (CaRMS) ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        administrationManagementModule = new AdministrationManagementModule();
                        registrationManagementModule = new RegistrationManagementModule();
                        appointmentManagementModule = new AppointmentManagementModule();
                        menuMain();
    
                    } catch (InvalidLoginCredentialsException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    currentStaffEntity = null;

                    break;
                } else {
                    System.out.println(INVALID_OPTION);
                }
            }

            if (response == 2) {
                break;
            }
        }
    }
    
    private void doLogin() throws InvalidLoginCredentialsException {

        Scanner scanner = new Scanner(System.in);
        String userName = "";
        String password = "";

        System.out.println("*** CaRMS System :: Login ***\n");
        System.out.print("Enter email> ");
        userName = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (userName.length() > 0 && password.length() > 0) {
            currentStaffEntity = staffSessionBeanRemote.staffLogin(userName, password);
        } else {
            throw new InvalidLoginCredentialsException("Missing login credential!");
        }
    }
    
    private void menuMain() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Main ***\n");
            System.out.println("You are login as " + currentStaffEntity.getFirstName() + " " + currentStaffEntity.getLastName()+"\n");
            System.out.println("1: Registration Operation");
            System.out.println("2: Appointment Operation");
            System.out.println("3: Administration Operation");
            System.out.println("4: Logout\n");
            
            response = 0;

//            while (response < 1 || response > 4) {
//
//                System.out.print("> ");
//
//                response = scanner.nextInt();
//
//                try {
//                    if (response == 1) {
//                        salesManagementModule.salesManagerMenu();
//                    } else if (response == 2) {
//                        operationManagementModule.operationMenu();
//                    } else if (response == 3) {
//                        customerServiceModule.customerServiceExMenu();
//                    } else if (response == 4) {
//                        allocateCar();
//                    } else if (response == 5) {
//                        currentEmployeeEntity = null;
//                        break;
//                    } else {
//                        System.out.println("Invalid option, please try again!\n");
//                    }
//
//                } catch (InvalidAccessRightException ex) {
//                    System.out.println("Invalid option, please try again!: " + ex.getMessage() + "\n");
//                }
//            }
//
//            if (response == 5) {
//                break;
//            }
        }
    }
}
