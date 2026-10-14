# School Simulation (Java)

A day-by-day simulation of a training school, written in Java for the Programming II module
(COMP1322) at the University of Southampton, March 2026.

Each simulated day the school opens courses for its subjects, assigns qualified instructors,
enrols students who haven't yet passed a subject, and moves every course one day closer to
starting or finishing. Random events keep it moving: new students arrive, instructors are hired
or quit, and courses with no instructor or students are cancelled.

## Design

- **Object-oriented model:** `Person` is the base of `Student` and the abstract `Instructor`, which
  has four subclasses (`Teacher`, `Demonstrator`, `OOTrainer`, `GUITrainer`), each deciding which
  subjects it can teach.
- **`School`** holds subjects, courses, students and instructors, with overloaded `add` and
  `remove` methods, and runs the daily logic.
- **`Administrator`** loads the configuration, runs the simulation and prints a daily report.
- **Validation:** the configuration loader reports malformed lines, missing values and unknown
  entity types with their line numbers, and illegal operations (enrolling on a full course,
  assigning an unqualified instructor) are blocked safely.
- **Save and resume:** at the end of a run the whole state is written to a readable
  `<name>.save.txt` file. Passing that file back in rebuilds every object and relationship and
  carries on from the same point.

## Usage

```bash
javac *.java
java Administrator mySchool.txt 10
```

The first argument is a configuration file (see `mySchool.txt`) or a `.save.txt` file to resume
from. The second is the number of days to simulate.
