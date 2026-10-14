// Represent a class instance that is either waiting to start or currently running.
public class Course {
    private Subject subject;
    private int daysUntilStarts;
    private int daysToRun;
    private Student[] students;
    private int enrolledCount;
    private Instructor instructor;
    private boolean isCancelled;

    public Course(Subject subject, int daysUntilStarts) {
        this.subject = subject;
        this.daysUntilStarts = daysUntilStarts;
        this.daysToRun = subject.getDuration();
        this.students = new Student[3];
        this.enrolledCount = 0;
        this.instructor = null;
        this.isCancelled = false;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public int getStatus() {
        if (this.daysUntilStarts > 0) {
            return -this.daysUntilStarts;
        } else if (this.daysToRun > 0) {
            return this.daysToRun;
        } else {
            return 0;
        }
    }

    // Process the daily timer logic for the course.
    public void aDayPasses() {
        if (this.isCancelled || this.daysToRun == 0) {
            return;
        }

        if (this.daysUntilStarts > 0) {
            this.daysUntilStarts--;
            if (this.daysUntilStarts == 0) {
                if (this.instructor == null || this.enrolledCount == 0) {
                    this.isCancelled = true;
                    if (this.instructor != null) {
                        this.instructor.unassignCourse();
                        this.instructor = null; 
                    }
                }
            }
        } else if (this.daysToRun > 0) {
            this.daysToRun--;
            if (this.daysToRun == 0) {
                for (int i = 0; i < this.enrolledCount; i++) {
                    this.students[i].graduate(this.subject);
                }
                if (this.instructor != null) {
                    this.instructor.unassignCourse();
                    this.instructor = null;
                }
            }
        }
    }

    // Enrol a student into the course if capacity allows.
    public boolean enrolStudent(Student student) {
        if (this.enrolledCount >= 3 || this.daysUntilStarts <= 0) {
            return false;
        }
        this.students[this.enrolledCount] = student;
        this.enrolledCount++;
        return true;
    }

    public int getSize() {
        return this.enrolledCount;
    }

    public Student[] getStudents() {
        Student[] enrolled = new Student[this.enrolledCount];
        for (int i = 0; i < this.enrolledCount; i++) {
            enrolled[i] = this.students[i];
        }
        return enrolled;
    }

    // Attempt to assign an instructor to the course returning false to avoid exceptions.
    public boolean setInstructor(Instructor instructor) {
        if (instructor.canTeach(this.subject)) {
            this.instructor = instructor;
            return true;
        }
        return false;
    }

    public boolean hasInstructor() {
        return this.instructor != null;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public int getDaysUntilStarts() { 
        return this.daysUntilStarts; 
    }
    
    public int getDaysToRun() { 
        return this.daysToRun; 
    }
    
    public Instructor getInstructor() { 
        return this.instructor; 
    }
    
    // Restore the course to a specific state when loading from a save file.
    public void overrideTimers(int untilStarts, int toRun, boolean cancelled) {
        this.daysUntilStarts = untilStarts;
        this.daysToRun = toRun;
        this.isCancelled = cancelled;
    }
}