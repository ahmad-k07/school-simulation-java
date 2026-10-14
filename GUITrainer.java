// Specialised trainer for GUI topics.
public class GUITrainer extends Teacher {
    public GUITrainer(String name, char gender, int age) {
        super(name, gender, age);
    }

    @Override
    public boolean canTeach(Subject subject) {
        int spec = subject.getSpecialism();
        return spec == 1 || spec == 2 || spec == 4;
    }
}