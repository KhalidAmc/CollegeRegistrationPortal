package ca.algonquin.portal.model;

/** Represents a course available for student registration. */
public class Course {
    private final long id;
    private String code;
    private String title;
    private String description;
    private String term;
    private int credits;
    private int capacity;
    private boolean active;

    public Course(long id, String code, String title, String description, String term, int credits, int capacity, boolean active) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.term = term;
        this.credits = credits;
        this.capacity = capacity;
        this.active = active;
    }

    public long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
