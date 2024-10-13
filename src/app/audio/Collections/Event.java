package app.audio.Collections;

public class Event {
    private String name;

    private String description;

    private String date;

    /**
     * Constructor
     * @param name name of the event
     * @param description description of the event
     * @param date date of the event
     */
    public Event(final String name, final String description, final String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    /**
     * Getter for the name of the event
     * @return name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the event
     * @param name name of the event
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for the description of the event
     * @return description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the event
     * @param description description of the event
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for the date of the event
     * @return date of the event
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for the date of the event
     * @param date date of the event
     */
    public void setDate(final String date) {
        this.date = date;
    }
}
