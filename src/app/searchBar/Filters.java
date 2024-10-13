package app.searchBar;

import fileio.input.FiltersInput;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Filters {
    private String name;
    private String username;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;
    private String description;
    private String followers;

    public Filters(final FiltersInput filters) {
        this.name = filters.getName();
        this.username = filters.getUsername();
        this.album = filters.getAlbum();
        this.tags = filters.getTags();
        this.lyrics = filters.getLyrics();
        this.genre = filters.getGenre();
        this.releaseYear = filters.getReleaseYear();
        this.artist = filters.getArtist();
        this.owner = filters.getOwner();
        this.description = filters.getDescription();
        this.followers = filters.getFollowers();
    }
}
