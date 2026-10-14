// Represent a topic that can be taught at the school.
public class Subject {
    private int id;
    private int specialism;
    private int duration;
    private String description;

    public Subject(int id, int specialism, int duration, String description) {
        this.id = id;
        this.specialism = specialism;
        this.duration = duration;
        this.description = description;
    }
    
    public int getID() {
        return this.id;
    }

    public int getSpecialism() {
        return this.specialism;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}