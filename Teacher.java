// Standard teacher capable of teaching basic specialisms.
public class Teacher extends Instructor {
    public Teacher(String name, char gender, int age) {
        super(name, gender, age);
    }

    @Override
    public boolean canTeach(Subject subject) {
        int spec = subject.getSpecialism();
        return spec == 1 || spec == 2;
    }
}