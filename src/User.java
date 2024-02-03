
import java.util.ArrayList;

abstract class User {

        private String name;
        private String occupation;
        private String gender;
        private int age;
        private String password;
        public User(String name, String occupation, String gender, int age, String password){
                this.name = name;
                this.occupation = occupation;
                this.gender = gender;
                this.age = age;
                this.password=password;
        }
        protected String getName(){
                return name;
        }
        protected String getOccupation(){
                return occupation;
        }
        protected String getGender(){
                return gender;
        }
        protected int getAge(){
                return age;
        }

        /*
        The method checks to see if the username and the password given matches the data
         */
        protected boolean login(String username,String password) {
                if (this.password.equals(password) && this.name.equals(username)) {
                        return true;
                }
                return false;
        }
}


/*
 Doctor class represents the doctors that are working at the hospital
 */
class Doctor extends User{

        private String specialization;

        public Doctor(String name, String occupation, String gender, int age, String specialization, String password) {
                super(name, occupation, gender,age,password);
                this.specialization = specialization;
        }

        /*

         */
        public String prescribeMeds(String patientName, String med) {
                // Implementation for prescribing medications
                Patient patient=(Patient) Server.getInstance().getInfo(patientName,"patient");
                if(patient==null){
                        return "Patient doesn't exist";
                }
                patient.addMed(med);
                return "Success";
        }

        public String removeMeds(String patientName, String med) {
                // Implementation for prescribing medications
                Patient patient=(Patient) Server.getInstance().getInfo(patientName,"patient");
                if(patient==null){
                        return "Patient doesn't exist";
                }
                patient.removeMed(med);
                return "Success";
        }



        public String viewPatientNotes(String patientName) {
                Patient patient=(Patient)Server.getInstance().getInfo(patientName,"patient");
                return patient.viewNotes();
        }

        public ArrayList<Patient> getPatients() {
                return Server.getInstance().getPatientList(this);
        }
        public void setSpecialization(String newSpecialization){
                specialization=newSpecialization;
        }
        /*
       This method adds another patient to the userList on the server class
       @param the information of the new patient that is going to be added
       @return the String to say if it is successful or an error occured
        */
        public String addPatient(String name, String occupation, String gender, int age, String reasonForVisit,String password){
                return Server.getInstance().registerPatient(name, occupation, gender, age, reasonForVisit,password,super.getName(),this);

        }
}

/*
 Patient class represents the patients that are registered at the hospital
 */
class Patient extends User {
        private String reasonForVisit;
        private String apt;
        private ArrayList<String> meds;
        private String notes;
        private Doctor doctor;

        /*
        This constructor creates a new patient with the doctor that created it as the doctor of the patient
        @param is the information of the patient and the doctor that created the patient
         */
        public Patient(String name, String occupation, String gender, int age, String reasonForVisit,String password,Doctor doctor) {
                super(name,occupation,gender,age,password);
                this.reasonForVisit = reasonForVisit;
                this.apt="";
                this.meds=new ArrayList<String>();
                this.notes="";
                this.doctor=doctor;
        }
        public void payment() {
                apt="Resolved";
        }

        public String viewNotes() {
                return notes;
        }
        public void addNotes(String note){
                this.notes+="\n"+note;
        }
        public void makeApt() {
                apt="Scheduled";
        }
        public void addMed(String medication){
                meds.add(medication);
        }
        public void removeMed(String medication){
                meds.remove(medication);
        }

        public ArrayList<String> getMeds() {
                return meds;
        }

        public String pickDoctor(String doctorName) {
                Doctor doctor=(Doctor) Server.getInstance().getInfo(doctorName,"doctor");
                if(doctor==null){
                        return "Doctor doesn't exist";
                }
                this.doctor=doctor;
                return "Success";
        }
        public Doctor getDoctor(){
                return doctor;
        }

}

/*
Admin class represents the adminstrators that are working in the hospital
 */
class Admin extends User {

        /*
        Constructor that initializes everything
        @param is the information for the Admin object
         */
        public Admin(String name, String occupation, String gender, int age,String password) {
                super(name,occupation, gender, age,password);
        }

        /*
        This method deletes a user from the userList in the Server
        @param They are the information of the user that is going to be removed
        @return A String that indicate if an error has occurred when deleting or if it is successful
         */
        public String deleteUser(String name, String occupation, String gender, int age) {
                // Implementation for deleting a user
                return Server.getInstance().removeUser(name,occupation,gender,age );
        }

        /*
        This method adds another doctor to the userList on the server class
        @param the information of the new doctor that is going to be added
        @return the String to say if it is successful or an error occured
         */
        public String addDoctor(String name, String occupation, String gender, int age, String specialization, String password){
                return Server.getInstance().registerDoctor(name, occupation, gender, age, specialization, password,super.getName());

        }

        /*
         This method changes the specialization of a doctor
         @param The doctor name and the new specialization
         @return A string that indicate if an error occur or if it was a success
         */
        public String changeDoctor(String doctorName, String newSpecialization) {
                Doctor user= (Doctor) Server.getInstance().getInfo(doctorName,"doctor");
                if(user==null){
                        return "Doctor do not exist";
                }
                user.setSpecialization(newSpecialization);
                return "Success";
        }

        /*
       This method adds another admin to the userList on the server class
       @param the information of the new admin that is going to be added
       @return the String to say if it is successful or an error occured
        */
        public String addAdmin(String name, String occupation, String gender, int age,String password){
                return Server.getInstance().registerAdmin(name, occupation, gender,age,password,super.getName());
        }
}

/*
The Server class is a singleton class that stores and manages all data.
 */
class Server {
        private ArrayList<User> userList;
        private static Server instance;

        /*
        The constructor initializes the userList and adds an admin to it.
         */
        private Server() {userList = new ArrayList<>();
        userList.add(new Admin( "tester",  "", "", 0,"A"));}

        /*
        This method gets the instance of the Server class and restricts it so only one Server is created
        @return Return the Server instance
         */
        public static Server getInstance(){
                if (instance==null){
                        instance=new Server();
                }
                return instance;
        }
        public String getPatientApt() {
                // Implementation for retrieving patient's appointment information
                return ""; // Placeholder value, replace with actual logic
        }

        /*
        This method creates a new patient and add to userList, but checks to make sure class has clearance to register
        @param Information to create a patient class and the name of the object that called the method
        @return returns String indicating if the registraion is a success or what error has occured
         */
        public String registerPatient(String name, String occupation, String gender, int age, String reasonForVisit,String password, String username,Doctor doctor){
                if(!checkClearance(username,"doctor")){
                        return "No clearance";
                }
                if(checkDuplicates(name, occupation, gender, age)){
                        return "Duplicate data";
                }
                userList.add(new Patient(name, occupation, gender, age, reasonForVisit,password,doctor));
                return "Success";
        }

        /*
        This method creates a new doctor and add to userList, but checks to make sure class has clearance to register
        @param Information to create a doctor class and the name of the object that called the method
        @return returns String indicating if the registraion is a success or what error has occured
         */
        public String registerDoctor(String name, String occupation, String gender, int age, String specialization, String password,String username){
                if(!checkClearance(username,"admin")){
                        return "No clearance";
                }
                if(checkDuplicates(name, occupation, gender, age)){
                        return "Duplicate data";
                }
                userList.add(new Doctor(name, occupation, gender, age, specialization,password));
                return "Success";

        }

        /*
        This method creates a new admin and add to userList, but checks to make sure class has clearance to register
        @param Information to create an admin class and the name of the object that called the method
        @return returns String indicating if the registration succeed or error occured
         */
        public String registerAdmin(String name, String occupation, String gender, int age,String password,String username){
                if(!checkClearance(username,"admin")){
                        return "No clearance";
                }
                if(checkDuplicates(name, occupation, gender, age)){
                        return "Duplicate data";
                }
                userList.add(new Admin(name, occupation, gender, age,password));
                return "Success";

        }

        /*
        This method returns that class that is requested
        @param is the name of the object being looked for and the type of person the user is
        @return is the user, unless there is no user that matches, which returns null
         */
        public User getInfo(String username, String type) {
                for(User user: userList){
                        if(user.getName().equals(username)){
                                if(checkClearance(username,type)){
                                        return user;
                                }
                        }
                }
                return null; // Placeholder value, replace with actual logic
        }


        /*
        This method removes a user from the userList
        @param The information about the user that is going to be removed
        @return The String indicating if the process was successful or an error has occured
         */
        public String removeUser(String username, String occupation, String gender, int age){
                if( !checkClearance(username,"admin")){
                        return "No clearance";
                }
                for(User user: userList){
                        if(user.getName().equals(username)){
                              if(occupation.equals(user.getOccupation())&&gender.equals(user.getGender())&&age==user.getAge()){
                                      return "Success";
                                }
                        }
                }
                return "Unable to find user";
        }

        /*
        This method checks to see if the username is actually from a user with correct clearance
        @param the username of the object and the type of user it is supposed to be
        @return returns true if the test passes and false if test fails
         */
        private boolean checkClearance(String username,String type){
                for(User user: userList){
                        if(user.getName().equals(username)){
                                if(type.equals("doctor")&&
                                                ((user.getClass().equals(Doctor.class)))
                                ){
                                        return true;
                                }
                                else if(type.equals("admin")&&
                                        (user.getClass().equals(Admin.class)))
                                {
                                        return true;
                                }
                                else if(type.equals("patient")&&
                                        (user.getClass().equals(Patient.class)))
                                {
                                        return true;
                                }

                        }
                }
                return false;
        }

        /*
        This method checks to see if the given data is already in the userList
        @param the information of the new object that is trying to get added to userList
        @return returns true if there is a match in teh userList and false otherwise
         */
        private boolean checkDuplicates(String name, String occupation, String gender, int age) {
                for (User user : userList) {
                        if (user.getName().equals(name)) {
                                if(occupation.equals(user.getOccupation())&&gender.equals(user.getGender())&&age==user.getAge()){
                                        return true;
                                }
                        }
                }
                return false;
        }

        /*
        This check to see if the username and the password matches one of the users and returns that user
        @param the username and password of the user
        @return the user that matches with the username and password
         */
        public User login(String username, String password){
                for(User user: userList){
                        if(user.getName().equals(username)){
                                if(user.login(username,password)){
                                        return user;
                                }
                        }
                }
                return null;
        }

        public ArrayList<Patient> getPatientList(Doctor doctor) {
                ArrayList<Patient> list=new ArrayList<>();
                for(User user: userList){
                        if(user.getClass().equals(Patient.class)){
                                if(((Patient) user).getDoctor()==doctor){
                                        list.add((Patient)user);
                                }
                        }
                }
                return list;
        }
}