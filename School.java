import java.util.ArrayList;
import java.util.List;

// The core structure of the school managing all primary entity lists.
public class School {
    private String name;
    
    private List<Subject> subjects;
    private List<Course> courses;
    private List<Student> students;
    private List<Instructor> instructors;

    public School(String name) {
        this.name = name;
        this.subjects = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
        this.instructors = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void add(Subject subject) { this.subjects.add(subject); }
    public void remove(Subject subject) { this.subjects.remove(subject); }
    
    public void add(Course course) { this.courses.add(course); }
    public void remove(Course course) { this.courses.remove(course); }
    
    public void add(Student student) { this.students.add(student); }
    public void remove(Student student) { this.students.remove(student); }
    
    public void add(Instructor instructor) { this.instructors.add(instructor); }
    public void remove(Instructor instructor) { this.instructors.remove(instructor); }

    public List<Subject> getSubjects() { return this.subjects; }
    public List<Course> getCourses() { return this.courses; }
    public List<Student> getStudents() { return this.students; }
    public List<Instructor> getInstructors() { return this.instructors; }

    // Execute the core logic for a single simulated day.
    public void aDayAtSchool() {
        List<Course> newCourses = new ArrayList<>();
        for (Subject subject : this.subjects) {
            boolean hasOpenCourse = false;
            for (Course course : this.courses) {
                if (course.getSubject().getID() == subject.getID()) {
                    if (!course.isCancelled() && course.getStatus() != 0 && course.getSize() < 3) {
                        hasOpenCourse = true;
                        break;
                    }
                }
            }
            if (!hasOpenCourse) {
                newCourses.add(new Course(subject, 2));
            }
        }
        this.courses.addAll(newCourses);

        for (Course course : this.courses) {
            if (!course.hasInstructor()) {
                for (Instructor inst : this.instructors) {
                    if (inst.getAssignedCourse() == null) {
                        if (course.setInstructor(inst)) {
                            inst.assignCourse(course);
                            break; 
                        }
                    }
                }
            }
        }

        for (Student student : this.students) {
            boolean isFree = true;
            for (Course course : this.courses) {
                for (Student enrolled : course.getStudents()) {
                    if (enrolled == student) {
                        isFree = false;
                        break;
                    }
                }
                if (!isFree) break;
            }

            if (isFree) {
                for (Course course : this.courses) {
                    if (!student.hasCertificate(course.getSubject())) {
                        if (course.enrolStudent(student)) {
                            break; 
                        }
                    }
                }
            }
        }

        for (Course course : this.courses) {
            course.aDayPasses();
        }

        this.courses.removeIf(course -> course.isCancelled() || course.getStatus() == 0);
    }

    @Override
    public String toString() {
        return "School: " + this.name + "\n" +
               "Subjects: " + this.subjects.size() + "\n" +
               "Courses: " + this.courses.size() + "\n" +
               "Instructors: " + this.instructors.size() + "\n" +
               "Students: " + this.students.size();
    }
}