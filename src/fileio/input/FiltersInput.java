package fileio.input;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class FiltersInput {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String album;
    @Getter
    @Setter
    private ArrayList<String> tags;
    @Getter
    @Setter
    private String lyrics;
    @Getter
    @Setter
    private String genre;
    @Getter
    @Setter
    private String releaseYear; // pentru search song/episode -> releaseYear
    @Getter
    @Setter
    private String artist;
    @Getter
    @Setter
    private String owner; // pentru search playlist si podcast
    @Getter
    @Setter
    private String followers; // pentru search playlist -> followers
    @Getter
    @Setter
    private String description; // pentru search album -> description

    @Getter
    @Setter
    private String username;

    public FiltersInput() {
    }

    @Override
    public String toString() {
        return "FilterInput{"
                + ", name='" + name + '\''
                + ", album='" + album + '\''
                + ", tags=" + tags
                + ", lyrics='" + lyrics + '\''
                + ", genre='" + genre + '\''
                + ", releaseYear='" + releaseYear + '\''
                + ", artist='" + artist + '\''
                + ", owner='" + owner + '\''
                + ", followers='" + followers + '\''
                + '}';
    }

}
