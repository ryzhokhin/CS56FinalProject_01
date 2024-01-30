
import java.util.ArrayList;

interface User {

        String getName();
        String getOccupation();
        String getGender();
        int getAge();
        boolean logIn();
}

class Doctor implements User {
        private String name;
        private String occupation;
        private String gender;
        private int age;
        private String specialization;
        private ArrayList<Patient> patientList;

        public Doctor(String name, String occupation, String gender, int age, String specialization) {
                this.name = name;
                this.occupation = occupation;
                this.gender = gender;
                this.age = age;
                this.specialization = specialization;
                this.patientList = new ArrayList<>();
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public String getOccupation() {
                return occupation;
        }

        @Override
        public String getGender() {
                return gender;
        }

        @Override
        public int getAge() {
                return age;
        }

        @Override
        public boolean logIn() {
                // Implementation for Doctor login
                return true; // Placeholder value, replace with actual logic
        }

        public void prescribeMeds() {
                // Implementation for prescribing medications
        }

        public void aptNotes() {
                // Implementation for handling appointment notes
        }

        public void viewPatients() {
                // Implementation for viewing patient information
        }
        public void setSpecialization(String newSpecialization){
                return;
        }
}

class Patient implements User {
        private String name;
        private String occupation;
        private String gender;
        private int age;
        private Long reasonForVisit;
        private String apt;
        private String meds;
        private String notes;
        private Doctor doctor;

        public Patient(String name, String occupation, String gender, int age, Long reasonForVisit) {
                this.name = name;
                this.occupation = occupation;
                this.gender = gender;
                this.age = age;
                this.reasonForVisit = reasonForVisit;
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public String getOccupation() {
                return occupation;
        }

        @Override
        public String getGender() {
                return gender;
        }

        @Override
        public int getAge() {
                return age;
        }

        @Override
        public boolean logIn() {
                // Implementation for Patient login
                return true; // Placeholder value, replace with actual logic
        }

        public void payment() {
                // Implementation for handling payments
        }

        public void viewNotes() {
                // Implementation for viewing notes
        }

        public void makeApt() {
                // Implementation for making appointments
        }

        public void pickDoctor(Doctor doctor) {
                this.doctor = doctor;
        }

}

class Admin implements User {
        private String name;
        private String occupation;
        private String gender;
        private int age;

        public Admin(String name, String occupation, String gender, int age) {
                this.name = name;
                this.occupation = occupation;
                this.gender = gender;
                this.age = age;
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public String getOccupation() {
                return occupation;
        }

        @Override
        public String getGender() {
                return gender;
        }

        @Override
        public int getAge() {
                return age;
        }

        @Override
        public boolean logIn() {
                return false;
        }

        public void deleteUser(User user) {
                // Implementation for deleting a user
        }

        public void changeDoctor(Doctor doctor, String newSpecialization) {
                doctor.setSpecialization(newSpecialization);
        }
}

class Server {
        private ArrayList<User> userList;

        public Server() {
                this.userList = new ArrayList<>();
        }

        public String getPatientApt(int id) {
                // Implementation for retrieving patient's appointment information
                return ""; // Placeholder value, replace with actual logic
        }

        public void aptReminder() {
                // Implementation for sending appointment reminders
        }

        public User getInfo() {
                // Implementation for retrieving user information
                return null; // Placeholder value, replace with actual logic
        }

        public void changeApt() {
                // Implementation for changing appointments
        }

        public void setInfo(String information, String type) {
                // Implementation for setting user information
        }
}