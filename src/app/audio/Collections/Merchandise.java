package app.audio.Collections;

import lombok.Getter;
import lombok.Setter;

public class Merchandise {

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private int price;

    public Merchandise(final String name, final String description, final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
