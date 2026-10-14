// Abstract base class for any individual qualified to teach.
public abstract class Instructor extends Person {
    private Course assignedCourse; 

    public Instructor(String name, char gender, int age) {
        super(name, gender, age);
        this.assignedCourse = null;
    }

    public void assignCourse(Course course) {
        this.assignedCourse = course;
    }

    public void unassignCourse() {
        this.assignedCourse = null;
    }

    public Course getAssignedCourse() {
        return this.assignedCourse;
    }

    public abstract boolean canTeach(Subject subject);
}