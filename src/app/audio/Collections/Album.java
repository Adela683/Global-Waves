package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
//import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
//import java.util.List;


@Getter
public final class Album extends AudioCollection {


    /**
     * Instantiates a new Audio collection.
     *
     * @param name  the name
     * @param owner the owner
     */
    public Album(final String name, final String owner, final int releaseYear,
                 final String description, final ArrayList<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
        this.name = name;
    }

    /**
     * Gets number of tracks.
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * This method returns the track at the given index.
     * @param index the index
     * @return the track by index
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    private ArrayList<Song> songs;

    private String name;

    private int releaseYear;

    private String description;

    @Getter
    private int totalLikes;

    /**
     * This method adds a like to the album.
     */
    public void addLike() {
        totalLikes++;
    }
}
