// Demonstrator assigned to assist with specific lab work.
public class Demonstrator extends Instructor {
    public Demonstrator(String name, char gender, int age) {
        super(name, gender, age);
    }

    @Override
    public boolean canTeach(Subject subject) {
        return subject.getSpecialism() == 2;
    }
}