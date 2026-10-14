import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;

// Main engine handling setup and file parsing.
public class Administrator {
    private School school;
    private Random random;
    private int personIDCounter;

    public Administrator(School school) {
        this.school = school;
        this.random = new Random();
        this.personIDCounter = 1;
    }

    public Administrator(School school, int savedCounter) {
        this.school = school;
        this.random = new Random();
        this.personIDCounter = savedCounter;
    }

    public void run() {
        while (true) {
            simulateOneDay();
            try { Thread.sleep(1000); } catch (InterruptedException e) { break; }
        }
    }

    public void run(int days) {
        for (int i = 1; i <= days; i++) {
            System.out.println("\n========== DAY " + i + " ==========");
            simulateOneDay();
            reportState();
        }
    }

    // Handle the random daily events before executing the main school logic.
    private void simulateOneDay() {
        int newStudents = this.random.nextInt(3);
        for (int i = 0; i < newStudents; i++) {
            char gender = this.random.nextBoolean() ? 'M' : 'F';
            int age = 18 + this.random.nextInt(40);
            this.school.add(new Student("Student_" + this.personIDCounter++, gender, age));
        }

        char gen; int instAge; 
        
        if (this.random.nextInt(100) < 20) {
            gen = this.random.nextBoolean() ? 'M' : 'F';
            instAge = 25 + this.random.nextInt(40);
            this.school.add(new Teacher("Inst_" + this.personIDCounter++, gen, instAge));
        }
        if (this.random.nextInt(100) < 10) {
            gen = this.random.nextBoolean() ? 'M' : 'F';
            instAge = 25 + this.random.nextInt(40);
            this.school.add(new Demonstrator("Inst_" + this.personIDCounter++, gen, instAge));
        }
        if (this.random.nextInt(100) < 5) {
            gen = this.random.nextBoolean() ? 'M' : 'F';
            instAge = 25 + this.random.nextInt(40);
            this.school.add(new OOTrainer("Inst_" + this.personIDCounter++, gen, instAge));
        }
        if (this.random.nextInt(100) < 5) {
            gen = this.random.nextBoolean() ? 'M' : 'F';
            instAge = 25 + this.random.nextInt(40);
            this.school.add(new GUITrainer("Inst_" + this.personIDCounter++, gen, instAge));
        }

        this.school.aDayAtSchool();

        List<Instructor> instLeaving = new ArrayList<>();
        for (Instructor inst : this.school.getInstructors()) {
            if (inst.getAssignedCourse() == null && this.random.nextInt(100) < 20) {
                instLeaving.add(inst);
            }
        }
        for (Instructor inst : instLeaving) this.school.remove(inst);

        List<Student> studentsLeaving = new ArrayList<>();
        int totalSubjects = this.school.getSubjects().size();
        for (Student student : this.school.getStudents()) {
            if (totalSubjects > 0 && student.getCertificates().size() == totalSubjects) {
                studentsLeaving.add(student);
            } else {
                boolean isFree = true;
                for (Course course : this.school.getCourses()) {
                    for (Student enrolled : course.getStudents()) {
                        if (enrolled == student) { isFree = false; break; }
                    }
                    if (!isFree) break;
                }
                if (isFree && this.random.nextInt(100) < 5) studentsLeaving.add(student);
            }
        }
        for (Student student : studentsLeaving) this.school.remove(student);
    }

    private void reportState() {
        System.out.println("--- COURSES ---");
        for (Course course : this.school.getCourses()) {
            System.out.print(course.getSubject().getDescription() + " (Status: " + course.getStatus() + ") - Enrolled: ");
            for (Student s : course.getStudents()) System.out.print(s.getName() + " ");
            System.out.println();
        }

        System.out.println("--- STUDENTS ---");
        for (Student student : this.school.getStudents()) {
            System.out.print(student.getName() + " | Certs: " + student.getCertificates().size() + " | Course: ");
            boolean inCourse = false;
            for (Course course : this.school.getCourses()) {
                for (Student s : course.getStudents()) {
                    if (s == student) {
                        System.out.print(course.getSubject().getDescription());
                        inCourse = true; break;
                    }
                }
                if (inCourse) break;
            }
            if (!inCourse) System.out.print("None");
            System.out.println();
        }

        System.out.println("--- INSTRUCTORS ---");
        for (Instructor inst : this.school.getInstructors()) {
            System.out.print(inst.getName() + " (" + inst.getClass().getSimpleName() + ") | Assigned: ");
            if (inst.getAssignedCourse() != null) System.out.println(inst.getAssignedCourse().getSubject().getDescription());
            else System.out.println("None");
        }
    }

    // Implement custom save logic here to meet extension requirements.
    public void saveSimulation(String filename) {
        String saveFile = filename.replace(".txt", "").replace(".save", "") + ".save.txt";
        
        try (PrintWriter out = new PrintWriter(new FileWriter(saveFile))) {
            out.println("school:" + this.school.getName());
            out.println("counter:" + this.personIDCounter);
            
            for (Subject s : this.school.getSubjects()) {
                out.println("subject:" + s.getDescription() + "," + s.getID() + "," + s.getSpecialism() + "," + s.getDuration());
            }
            
            for (Student s : this.school.getStudents()) {
                StringBuilder certs = new StringBuilder();
                for (int c : s.getCertificates()) certs.append(c).append(";");
                out.println("student:" + s.getName() + "," + s.getGender() + "," + s.getAge() + "," + certs.toString());
            }
            
            for (Instructor i : this.school.getInstructors()) {
                out.println(i.getClass().getSimpleName() + ":" + i.getName() + "," + i.getGender() + "," + i.getAge());
            }
            
            for (Course c : this.school.getCourses()) {
                String instName = c.hasInstructor() ? c.getInstructor().getName() : "null";
                StringBuilder stuNames = new StringBuilder();
                for (Student s : c.getStudents()) stuNames.append(s.getName()).append(";");
                
                out.println("course:" + c.getSubject().getID() + "," + c.getDaysUntilStarts() + "," + 
                            c.getDaysToRun() + "," + c.isCancelled() + "," + instName + "," + stuNames.toString());
            }
            System.out.println("\n[SYSTEM] Simulation saved successfully to: " + saveFile);
        } catch (IOException e) {
            System.err.println("Error saving simulation: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: Please provide exactly two arguments: <filename> <daysToRun>");
            return;
        }

        String filename = args[0];
        int daysToRun;

        try {
            daysToRun = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: The number of days must be a numeric integer.");
            return;
        }

        boolean isResume = filename.endsWith("save.txt");
        Administrator admin = loadConfiguration(filename, isResume);
        
        if (admin != null) {
            admin.run(daysToRun);
            admin.saveSimulation(filename);
        }
    }

    // Read the configuration file line by line utilizing try catch blocks to avoid crashes.
    private static Administrator loadConfiguration(String filename, boolean isResume) {
        School school = null;
        int savedCounter = 1;
        List<String> courseLines = new ArrayList<>(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(":", 2);
                if (parts.length != 2) {
                    System.err.println("Error on line " + lineNumber + ": Malformed line.");
                    return null; 
                }

                String type = parts[0].trim();
                String data = parts[1].trim();

                if (type.equals("school")) {
                    school = new School(data);
                } else if (school == null) {
                    System.err.println("Error: School name must be defined first in the file.");
                    return null;
                } else if (type.equals("counter")) {
                    savedCounter = Integer.parseInt(data);
                } else if (type.equals("course")) {
                    courseLines.add(data); 
                } else {
                    parseEntity(type, data, school, lineNumber, isResume);
                }
            }
            
            if (isResume) {
                for (String cData : courseLines) {
                    reconstructCourse(cData, school);
                }
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("Error: File '" + filename + "' could not be found.");
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
        return new Administrator(school, savedCounter);
    }

    private static void parseEntity(String type, String data, School school, int lineNumber, boolean isResume) throws Exception {
        String[] fields = data.split(",");
        for (int i = 0; i < fields.length; i++) fields[i] = fields[i].trim();

        try {
            switch (type) {
                case "subject":
                    school.add(new Subject(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Integer.parseInt(fields[3]), fields[0]));
                    break;
                case "student":
                    Student s = new Student(fields[0], fields[1].charAt(0), Integer.parseInt(fields[2]));
                    if (isResume && fields.length == 4 && !fields[3].isEmpty()) {
                        for (String certId : fields[3].split(";")) {
                            if (!certId.isEmpty()) {
                                for(Subject sub : school.getSubjects()){
                                    if(sub.getID() == Integer.parseInt(certId)){
                                        s.graduate(sub);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    school.add(s);
                    break;
                case "Teacher": school.add(new Teacher(fields[0], fields[1].charAt(0), Integer.parseInt(fields[2]))); break;
                case "Demonstrator": school.add(new Demonstrator(fields[0], fields[1].charAt(0), Integer.parseInt(fields[2]))); break;
                case "OOTrainer": school.add(new OOTrainer(fields[0], fields[1].charAt(0), Integer.parseInt(fields[2]))); break;
                case "GUITrainer": school.add(new GUITrainer(fields[0], fields[1].charAt(0), Integer.parseInt(fields[2]))); break;
                default: throw new Exception("Error on line " + lineNumber + ": Unknown type '" + type + "'.");
            }
        } catch (Exception e) {
            throw new Exception("Error on line " + lineNumber + ": Invalid data format.");
        }
    }

    // Reconstruct the active courses by manually linking the correct instructor and students.
    private static void reconstructCourse(String data, School school) {
        String[] fields = data.split(",", -1);
        int subId = Integer.parseInt(fields[0]);
        Subject targetSubject = null;
        
        for (Subject s : school.getSubjects()) {
            if (s.getID() == subId) { targetSubject = s; break; }
        }
        if (targetSubject == null) return;

        Course course = new Course(targetSubject, 999);

        String instName = fields[4];
        if (!instName.equals("null")) {
            for (Instructor inst : school.getInstructors()) {
                if (inst.getName().equals(instName)) {
                    course.setInstructor(inst);
                    inst.assignCourse(course);
                    break;
                }
            }
        }

        if (fields.length >= 6 && !fields[5].isEmpty()) {
            for (String stuName : fields[5].split(";")) {
                if (!stuName.isEmpty()) {
                    for (Student student : school.getStudents()) {
                        if (student.getName().equals(stuName)) {
                            course.enrolStudent(student);
                            break;
                        }
                    }
                }
            }
        }
        
        course.overrideTimers(Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), Boolean.parseBoolean(fields[3]));
        
        school.add(course);
    }
}