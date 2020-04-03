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
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import util.exception.AppointmentNotFoundException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidLoginException;
import util.exception.PatientNotFoundException;

public class MainApp {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;

    private PatientEntity currentPatientEntity;
    private StaffEntity currentStaffEntity;
    private DoctorEntity currentDoctorEntity;

    public MainApp() {
    }

    MainApp(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public void runApp() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to AMS Client ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    registerPatient();
                } else if (response == 2) {

                    try {
                        doLogin();
                        menuMain();
                    } catch (InvalidLoginException ex) {
                    }
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String identityNumber = "";
        String securityCode = "";

        System.out.println("*** AMS Client :: Login ***\n");
        System.out.print("Enter Identity Number> ");
        identityNumber = scanner.nextLine().trim();
        System.out.print("Enter Security Code> ");
        securityCode = scanner.nextLine().trim();

        if (identityNumber.length() > 0 && securityCode.length() > 0) {
            try {
                currentPatientEntity = patientEntityControllerRemote.patientLogin(identityNumber, securityCode);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginException ex) {
                System.out.println("Invalid login: " + ex.getMessage() + "\n");
                throw new InvalidLoginException();
            }
        } else {
            System.out.println("Invalid login!");
        }
    }

    // get details and register patient into database
    private void registerPatient() {
        Scanner scanner = new Scanner(System.in);
        PatientEntity newPatient = new PatientEntity();

        System.out.println("*** AMS Client :: Register ***\n");

        List<PatientEntity> patients = patientEntityControllerRemote.retrieveAllPatients();

        System.out.print("Enter Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        for (PatientEntity patient : patients) {
            if (patient.getIdentityNumber().equals(identityNumber)) {
                System.out.println("Patient is already created.");
                break;
            } else {
                newPatient.setIdentityNumber(identityNumber);
                System.out.print("Enter Security Code> ");
                newPatient.setSecurityCode(scanner.nextLine().trim());
                System.out.print("Enter First Name> ");
                newPatient.setFirstName(scanner.nextLine().trim());
                System.out.print("Enter Last Name> ");
                newPatient.setLastName(scanner.nextLine().trim());
                System.out.print("Enter Gender> ");
                newPatient.setGender(scanner.nextLine().trim());
                System.out.print("Enter Age> ");
                newPatient.setAge(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Enter Phone> ");
                newPatient.setPhone(scanner.nextLine().trim());
                System.out.print("Enter Address> ");
                newPatient.setAddress(scanner.nextLine().trim());

                List<PatientEntity> patientEntities = patientEntityControllerRemote.retrieveAllPatients();
                newPatient.setPatientId((long) patientEntities.size() + 1);

                patientEntityControllerRemote.createNewPatient(newPatient);
                System.out.println("You has been registered successfully!\n");
                break;
            }
        }
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** AMS Client :: Main ***\n");
            System.out.println("You are login as " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + "\n");
            System.out.println("1: View Appointments");
            System.out.println("2: Add Appointment");
            System.out.println("3: Cancel Appointment");
            System.out.println("4: Logout\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    viewPatientAppointments();
                } else if (response == 2) {
                    addAppointment();
                } else if (response == 3) {
                    cancelAppointment();
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

    private void viewPatientAppointments() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** AMS Client :: View Appointments ***\n");

        System.out.print("Enter Patient Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        // search appointment set by patient
        // search patient
        try {
            currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

        System.out.println();
        System.out.println("Appointments:");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%s%s%s%s\n", "Id|", "Date      |", "Time  |", "Doctor");

        DateFormat df = new SimpleDateFormat("HH:mm");
        DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(identityNumber)) {
                    System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + datef.format(appointmentEntity.getDate()), "| " + df.format(appointmentEntity.getTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }
        } else {
            System.out.println("No appointments made.");
        }
        System.out.println();
    }

    private void addAppointment() {
        Scanner scanner = new Scanner(System.in);
        AppointmentEntity newAppointmentEntity = new AppointmentEntity();
        String inputDate = "";
        Date currentDate = new Date();

        System.out.println("*** CARS :: Appointment Operation :: Add Appointment ***\n");
        System.out.println("Doctor:");
        // list all doctor in database (id, firstname lastname)
        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%s%s\n", "Id", "| Name");

        for (DoctorEntity doctorEntity : doctorEntities) {
            System.out.printf("%s%s\n", doctorEntity.getDoctorId().toString(), "| " + doctorEntity.getFirstName() + " " + doctorEntity.getLastName());
        }

        System.out.println();
        System.out.print("Enter Doctor Id> ");
        Long doctorId = scanner.nextLong();

        try {
            currentDoctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }

        scanner.nextLine();

        try {
            System.out.print("Enter Date> ");
            inputDate = scanner.nextLine().trim();

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(inputDate);
            currentDate = sdf.parse(sdf.format(new Date()));

            if (date.after(currentDate)) {
                newAppointmentEntity.setDate(date);

                Calendar cal = Calendar.getInstance();
                Calendar today = Calendar.getInstance();
                String availability[] = new String[16];

                long ONE_MINUTE_IN_MILLIS = 60000;
                cal.set(Calendar.HOUR_OF_DAY, 9);
                cal.set(Calendar.MINUTE, 00);

                long t = cal.getTimeInMillis();
                Date tempTime = new Date(t);
                DateFormat df = new SimpleDateFormat("HH:mm"); // dd-MM-yy date

                for (int i = 0; i < 16; i++) { // an array of time slots from 0900 to 1600
                    availability[i] = df.format(tempTime);
                    //System.out.println(availability[i]);
                    t += 30 * ONE_MINUTE_IN_MILLIS;
                    tempTime = new Date(t);
                }

                long timeToday = today.getTimeInMillis();
                Date dateToday = new Date(timeToday);

                //availability
                System.out.println("Availability for " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() + " on " + inputDate + ":");
                List<AppointmentEntity> appointments = appointmentEntityControllerRemote.retrieveAllAppointments();
                if (appointments != null) {
                    // for loop to keep track of 30 mins interval
                    for (int i = 0; i < 16; i++) { // i == 16 so it will only stop at 4.30pm
                        for (AppointmentEntity appointment : appointments) { // date check
                            // if date compare == 0, time not equal, print time
                            Date temp = appointment.getTime();
                            String stringTime = df.format(temp);
                            if (date.compareTo(appointment.getDate()) == 0 && stringTime.equals(availability[i]) && (Objects.equals(appointment.getDoctor().getDoctorId(), doctorId))) {
                                availability[i] = "";
                            }
                        }
                    }
                }

                for (int i = 0; i < 16; i++) { // print availabililty from array
                    if (availability[i].equals("")) {
                        System.out.print("");
                    } else {
                        System.out.print(availability[i] + " ");
                    }
                }
                System.out.println();

                System.out.print("Enter Time> ");
                String inputTime = scanner.nextLine().trim();
                try {
                    DateFormat formatter = new SimpleDateFormat("HH:mm");
                    Date time = formatter.parse(inputTime);
                    newAppointmentEntity.setTime(time);

                } catch (ParseException ex) {
                    System.out.println("An error has occurred while retrieving date: " + ex.getMessage() + "\n");
                }

                newAppointmentEntity.setAppointmentId((long) appointments.size() + 1);

                //add appointment
                appointmentEntityControllerRemote.createAppointment(newAppointmentEntity, currentPatientEntity.getIdentityNumber(), doctorId);
                System.out.println("Appointment: " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " and " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() + " at " + inputTime + " on " + inputDate + " has been added.");
                System.out.println();

            } else {
                System.out.println("Appointment can only be booked at least one day in advanced!");
            }

        } catch (ParseException ex) {
            System.out.println("An error has occurred while retrieving date: " + ex.getMessage() + "\n");
        }
    }

    private void cancelAppointment() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Appointment Operation :: Cancel Appointment ***\n");

        System.out.println("Appointments:");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%s%s%s%s\n", "Id|", "Date      |", "Time  |", "Doctor");

        DateFormat df = new SimpleDateFormat("HH:mm");
        DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(currentPatientEntity.getIdentityNumber())) {
                    System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + datef.format(appointmentEntity.getDate()), "| " + df.format(appointmentEntity.getTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }

            System.out.println();
            System.out.print("Enter Appointment Id> ");
            Long appId = scanner.nextLong();

            try {
                AppointmentEntity appointmentEntity = appointmentEntityControllerRemote.retrieveAppointmentByAppointmentId(appId);
                appointmentEntityControllerRemote.cancelAppointment(appId);
                System.out.println("Appointment: " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " and " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName() + " at " + df.format(appointmentEntity.getTime()) + " on " + datef.format(appointmentEntity.getDate()) + " has been cancelled.");
            } catch (AppointmentNotFoundException ex) {
                System.out.println("No appointment found.\n");
            }

        } else {
            System.out.println("No appointments made.");
        }
        System.out.println();
    }
}
