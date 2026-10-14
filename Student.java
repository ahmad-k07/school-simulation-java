import java.util.ArrayList;

// Represent a student enrolled at the school.
public class Student extends Person {
    private ArrayList<Integer> certificates;

    public Student(String name, char gender, int age) {
        super(name, gender, age);
        this.certificates = new ArrayList<>();
    }

    // Award a certificate utilizing a defensive check to prevent duplicates.
    public void graduate(Subject subject) {
        if (!hasCertificate(subject)) {
            this.certificates.add(subject.getID());
        }
    }

    public ArrayList<Integer> getCertificates() {
        return this.certificates;
    }

    public boolean hasCertificate(Subject subject) {
        return this.certificates.contains(subject.getID());
    }
}