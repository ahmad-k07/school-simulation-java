COURSEWORK ATTEMPTED BY SECTION

Part 2: Subject and Course Classes
Part 3: The Person and Student Classes
Part 4: The Instructor Class and Subclasses
Part 5: The School Class
Part 6: Running the Simulation
Part 7: Reading a Simulation Configuration File

EXTENSIONS ATTEMPTED (Part 8) - Part 8.1 & 8.2 (Saving and Resuming): Implemented a custom text based save and resume feature in Administrator.java. The saveSimulation method exports the current state of all subjects, students, instructors, and active courses to a human readable .save.txt file. The resume logic parses this file line by line, recreates the entities, and accurately reconstructs the object relationships (e.g., re linking the correct instructor and enrolled students back to their ongoing courses, and restoring specific course timers).
- Part 8.3 (Exceptions): Added robust error handling across the simulation. The file parser loadConfiguration uses try-catch blocks to detect malformed lines, missing parameters, or unknown entity types, printing clear error messages with exact line numbers to System.err to prevent silent crashes. Additionally, defensive checks in School.java and Course.java safely prevent illegal runtime operations, such as enrolling students into a full course or assigning an unqualified instructor.
