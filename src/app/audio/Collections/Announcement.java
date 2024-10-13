package app.audio.Collections;

import lombok.Getter;

public class Announcement {

    @Getter
    private String name;

    @Getter
    private String description;

    /**
     * Constructor
     * @param name
     * @param description
     */
    public Announcement(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Setters
     * @param name the name of the announcement
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Setters
     * @param description the description of the announcement
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
