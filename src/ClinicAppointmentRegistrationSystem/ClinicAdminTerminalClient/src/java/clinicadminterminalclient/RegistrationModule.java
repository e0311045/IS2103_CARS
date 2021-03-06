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
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.AppointmentEntity;
import entity.ConsultationEntity;
import entity.StaffEntity;
import entity.PatientEntity;
import entity.DoctorEntity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import util.date.DateHelper;
import java.util.Calendar;
import java.util.regex.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.exception.DoctorNotFoundException;
import util.exception.PatientNotFoundException;

public class RegistrationModule {
    
    //SessionBeans
    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private RegistrationControllerRemote registrationControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    
    //Entities variable
    private StaffEntity currentStaffEntity;
    private PatientEntity currentPatientEntity;
    private DoctorEntity currentDoctorEntity;
    private AppointmentEntity currentAppointment;

    public RegistrationModule(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, RegistrationControllerRemote registrationControllerRemote, ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.registrationControllerRemote = registrationControllerRemote;
        this.consultationEntityControllerRemote = consultationEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public RegistrationModule(StaffEntity currentStaffEntity, RegistrationControllerRemote registrationControllerRemote) {
        //this();

        this.currentStaffEntity = currentStaffEntity;
        this.registrationControllerRemote = registrationControllerRemote;
    }

    public void menuRegistration() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Registration Operation ***\n");
            System.out.println("1: Register New Patient");
            System.out.println("2: Register Walk-In Consultation");
            System.out.println("3: Register Consultation By Appointment");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    registerPatient();
                } else if (response == 2) {
                    consultationByWalkIn();
                } else if (response == 3) {
                    consultationByAppointment();
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

    //Register new Patient
    private void registerPatient() {
        Scanner scanner = new Scanner(System.in);
        PatientEntity newPatient = new PatientEntity();

        System.out.println("*** CARS :: Registration Operation :: Register Patient ***\n");

        List<PatientEntity> patients = patientEntityControllerRemote.retrieveAllPatients();
        
        System.out.print("Enter Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        for (PatientEntity patient : patients) {
            if (patient.getIdentityNumber().equals(identityNumber)) {
                System.out.println("Patient is already created.");
                break;
            } else {
                newPatient.setIdentityNumber(identityNumber);
                //Validating all inputs
                while(true){
                    System.out.print("Enter Password> ");
                    String password = scanner.nextLine().trim();
                    Pattern digitPattern = Pattern.compile("\\d{6}");
                    if(!digitPattern.matcher(password).matches()){
                        System.out.println("Password has to be 6 digit");
                    } else {
                        newPatient.setPassword(password);
                        break;
                    }                   
                }
                while(true){
                    System.out.print("Enter First Name> ");
                    String firstName = scanner.nextLine().trim();
                    if(firstName.length()>32){
                        System.out.println("Maximum first name length is 32");
                    }
                    else{
                        newPatient.setFirstName(firstName);
                        break;
                    }       
                }
                while(true){
                    System.out.print("Enter Last Name> ");
                    String lastName = scanner.nextLine().trim();
                    if(lastName.length()>32){
                        System.out.println("Maximum last name length is 32");
                    }
                    else{
                        newPatient.setLastName(lastName);
                        break;
                    }       
                }
                while(true){
                    System.out.print("Enter Gender> ");
                    String gender = scanner.nextLine().trim();
                    if(gender.length()>1){
                        System.out.println("Please input either 'F' or 'M'!");
                    }
                    else if(gender.charAt(0)=='F'||gender.charAt(0)=='M'){
                        newPatient.setGender(gender.charAt(0));
                        break;
                    } else {
                        System.out.println("Gender can only either be 'F' or 'M'!");
                    }                   
                }                
                while(true){
                    System.out.print("Enter Age> ");
                    Integer age = scanner.nextInt();
                    Pattern digitPattern = Pattern.compile("\\d{3}");
                    if(!digitPattern.matcher(age.toString()).matches()){
                        System.out.println("Age can only have max of 3 digits");
                    } else {
                        newPatient.setAge(age);
                        break;
                    }                   
                }
                scanner.nextLine();
                while(true){
                    System.out.print("Enter Phone> ");
                    String phone = scanner.nextLine().trim();
                    if(phone.length()>15){
                        System.out.println("Maximum phone length can only be 15");
                    } else {
                        newPatient.setPhone(phone);
                        break;
                    }                 
                }               
                while(true){
                    System.out.print("Enter Address> ");
                    String address = scanner.nextLine().trim();
                    if(address.length()>50){
                        System.out.println("Maximum address length can only be 50");
                    } else {
                        newPatient.setAddress(address);
                        break;
                    }                 
                }
                patientEntityControllerRemote.createNewPatient(newPatient);
                System.out.println("Patient has been registered successfully!\n");
                break;
            }
        }
    }

    private void consultationByWalkIn() throws ParseException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Registration Operation :: Register Walk-In Consultation ***\n");
        System.out.println("Doctor:\n");
        // list all doctor in database id, firstname lastname
        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%s%s\n", "Id", "| Name");

        for (DoctorEntity doctorEntity : doctorEntities) {
            System.out.printf("%s%s\n", doctorEntity.getDoctorId().toString(), "| " + doctorEntity.getFirstName() + " " + doctorEntity.getLastName());
        }

        System.out.println("Availability:\n");
        // list all slots available to book (3 hour mark from current time)
        // Depending on day, closing hours vary Mon-Wed last consultation is 17:30, Thurs 16:30 and Fri 17:00

        // check current time
        Calendar cal = Calendar.getInstance();
        //check if within operation hours
        Calendar operating = Calendar.getInstance();
        if ((operating.get(Calendar.HOUR_OF_DAY) == 9 && operating.get(Calendar.MINUTE) >= 00) || (operating.get(Calendar.HOUR_OF_DAY) == 16 && operating.get(Calendar.MINUTE) < 30)) {

            String availability[][] = new String[7][doctorEntities.size() + 1];
            availability[0][0] = "Time ";

            // doctorid in table row 0
            for (int i = 1; i < doctorEntities.size() + 1; i++) {
                availability[0][i] = Integer.toString(i);
            }

            long ONE_MINUTE_IN_MILLIS = 60000;
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);

            if (min >= 00 && min <= 29) {
                cal.set(Calendar.MINUTE, 30);
            } else {
                cal.set(Calendar.HOUR_OF_DAY, hour + 1);
                cal.set(Calendar.MINUTE, 00);
            }

            long t = cal.getTimeInMillis();

            // time in table column 0
            for (int i = 1; i < 7; i++) {
                Date time = new Date(t);
                DateFormat df = new SimpleDateFormat("HH:mm"); // dd-MM-yy date
                availability[i][0] = df.format(time);
                t += 30 * ONE_MINUTE_IN_MILLIS;
            }

            // create all empty sets first to avoid null exception in accessing array
            for (int i = 1; i < 7; i++) {
                for (int j = 1; j < doctorEntities.size() + 1; j++) {
                    availability[i][j] = "O"; // all empty
                }
            }

            // if consultation slot taken insert X
            for (int i = 1; i < 7; i++) {
                for (int j = 1; j < doctorEntities.size() + 1; j++) {
                    // if find(doctor, time) , print X // ConsultationNotFound
                    List<ConsultationEntity> consultations = consultationEntityControllerRemote.retrieveAllConsultations();
                    if (consultations != null) {
                        for (ConsultationEntity consultation : consultations) {
                            Date temp = consultation.getTime();
                            String tempTime = DateHelper.timeSDF.format(temp);
                            if ((consultation.getDoctor().getDoctorId().toString().equals(Integer.toString(j))) && tempTime.equals(availability[i][0])) {
                                availability[i][j] = "X";
                            }
                        }
                    }
                }
            }

            // print availability table
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < doctorEntities.size() + 1; j++) {
                    if (availability[i][j].equals("09:00")) {// opening
                        System.out.print(availability[i][j] + " |");
                    } else if (availability[i][j].equals("17:00")) { // near closing
                        System.out.println("No slots available after 5pm");
                        i = 6;
                        j = doctorEntities.size();
                    } else {
                        System.out.print(availability[i][j] + " |");
                    }
                }
                System.out.println("");
            }

            System.out.print("Enter Doctor Id> ");
            Long doctorId = scanner.nextLong();

            // search doctor
            try {
                currentDoctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
            } catch (DoctorNotFoundException ex) {
                System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
            }

            scanner.nextLine();

            System.out.print("Enter Patient Identity Number> ");
            String identityNumber = scanner.nextLine().trim();

            // search patient
            try {
                currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
            } catch (PatientNotFoundException ex) {
                System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
            }

            int docId = doctorId.intValue();

            // if Time avaiable is "O" -> add consultation at this timing
            Date currTime = new Date();

            for (int i = 1; i < 7; i++) {
                if (availability[i][docId].equals("O")) {// available
                    currTime = DateHelper.timeSDF.parse(availability[i][0]);
                    i = 6;
                }
            }

            System.out.println(currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " appointment with Dr. " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() + " has been booked at " + DateHelper.timeSDF.format(currTime) + ".\n Queue Number is: " + registrationControllerRemote.getQueue() + "\n");
            registrationControllerRemote.addQueue();

            // create consultation
            ConsultationEntity newConsultation = new ConsultationEntity();
            newConsultation.setTime(currTime);
            List<ConsultationEntity> consultations = consultationEntityControllerRemote.retrieveAllConsultations();
            newConsultation.setConsultationId((long) consultations.size() + 1);
            consultationEntityControllerRemote.createConsultation(newConsultation, identityNumber, doctorId);
        } else {
            System.out.println("There are no more consultations because clinic is either closed, closing soon or not opened yet.");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void consultationByAppointment() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Registration Operation :: Register Consultation By Appointment ***\n");

        System.out.print("Enter Patient Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        // search appointment set by patient
        // search patient
        try {
            currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

        System.out.println("Appointments:\n");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%s%s%s%s\n", "Id|", "Date      |", "Time  |", "Doctor");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(identityNumber)) {
                    System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + DateHelper.dateSDF.format(appointmentEntity.getAppointmentDate()), "| " + DateHelper.timeSDF.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }
        } else {
            System.out.println("No appointments made.");
        }

        //check if within operation hours
        Calendar operating = Calendar.getInstance();
        if ((operating.get(Calendar.HOUR_OF_DAY) == 9 && operating.get(Calendar.MINUTE) >= 00) || (operating.get(Calendar.HOUR_OF_DAY) == 16 && operating.get(Calendar.MINUTE) < 30)) {
            System.out.print("Enter Appointment Id> ");
            int appointmentId = scanner.nextInt();

            // get doctorId from appointment to constrict table availability
            if (appointmentEntities != null) {
                for (AppointmentEntity appointmentEntity : appointmentEntities) {
                    if (appointmentEntity.getAppointmentId() == appointmentId) {
                        currentAppointment = appointmentEntity;
                    }
                }

                // get time check against doctor availability in consultation
                Date currTime = currentAppointment.getAppointmentTime();

                List<ConsultationEntity> consultations = consultationEntityControllerRemote.retrieveAllConsultations();
                if (consultations != null) {
                    for (ConsultationEntity consultation : consultations) {
                        Date temp = consultation.getTime();
                        String tempTime = DateHelper.timeSDF.format(temp);
                        //consultation slot not available
                        if ((consultation.getDoctor().getDoctorId().toString().equals(currentAppointment.getDoctor().getDoctorId().toString())) && tempTime.equals(DateHelper.timeSDF.format(currTime))) {
                            System.out.println("Consultation slot is already taken.");
                        } else { // consultation available for appointment
                            System.out.println(currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " appointment is confirmed with Dr. " + currentAppointment.getDoctor().getFirstName() + " " + currentAppointment.getDoctor().getLastName() + " at " + DateHelper.timeSDF.format(currTime) + ".\n Queue Number is: " + registrationControllerRemote.getQueue() + "\n");
                            registrationControllerRemote.addQueue();

                            // create consultation
                            ConsultationEntity newConsultation = new ConsultationEntity();
                            newConsultation.setTime(currTime);
                            newConsultation.setConsultationId((long) consultations.size() + 1);
                            consultationEntityControllerRemote.createConsultation(newConsultation, identityNumber, currentAppointment.getDoctor().getDoctorId());
                        }
                    }
                }
            }
        } else {
            System.out.println("There are no more consultations because clinic is either closed, closing soon or not opened yet.");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }
}
