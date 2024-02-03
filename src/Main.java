public class Main {
    public static void main(String[] args) {
        Admin man=(Admin)Server.getInstance().login("tester","A");
        man.addDoctor("doc", "pain", "ga",3, "mmmmmmm", "password");
        Doctor doc=(Doctor)Server.getInstance().login("doc","password");
        System.out.println(doc.addPatient("pat", "haha", "trilinginaulaedff", 1902, "oid","abc"));
        Patient patient=(Patient)Server.getInstance().login("pat","abc");
        doc.prescribeMeds("pat","Totally legit");
        System.out.println(doc.getPatients());
        System.out.println(patient.getMeds());

    }

}
