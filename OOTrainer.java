// Specialised trainer for Object Oriented topics.
public class OOTrainer extends Teacher {
    public OOTrainer(String name, char gender, int age) {
        super(name, gender, age);
    }

    @Override
    public boolean canTeach(Subject subject) {
        int spec = subject.getSpecialism();
        return spec == 1 || spec == 2 || spec == 3;
    }
}