package app.user;

import app.Admin;
import  app.audio.Collections.Album;
import  app.audio.Collections.Merchandise;
import  app.audio.Collections.Event;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Artist extends LibraryEntry {

    @Getter
    @Setter
    private static List<Album> albums;
    @Getter
    private List<Merchandise> merchandise;
    @Getter
    private List<Event> events;


    @Getter
    private static String type = "artist";

    // Constructor pentru Artist
    public Artist(final String name) {
        super(name);
        albums = new ArrayList<>();
        merchandise = new ArrayList<>();
        events = new ArrayList<>();
        type = "artist";
    }

    /**
     * Returns the list of songs associated with the artist.
     *
     * @return The list of songs associated with the artist.
     */
    public List<Song> getSongs() {
        List<Song> rez = new ArrayList<>();
        for (Album a : albums) {
            var aux = a.getSongs();
            rez.addAll(aux);
        }
        return rez;
    }

    /**
     * Returns the list of albums associated with the artist.
     */
    public void addAlbum(final Album album) {
        albums.add(album);
    }

    /**
     * Returns the list of merchandise associated with the artist.
     */
    public void addMerchandise(final Merchandise merch) {
        merchandise.add(merch);
    }

    /**
     * Returns the list of events associated with the artist.
     */
    public void addEvent(final Event event) {
        events.add(event);
    }

    /**
     * Removes an album based on the provided username and album name.
     *
     * @param username  The username of the artist who owns the album.
     * @param albumName The name of the album to be removed.
     * @return A message indicating the success or failure of the album removal operation.
     */
    public static String removeAlbum(final String username, final String albumName) {

        // Get the artist associated with the provided username
        Artist artist = Admin.getArtist(username);

        // Check if the provided username is valid and is associated with an artist
        if (!isUserArtist(username)) {
            return "The username " + username + " is not an artist.";
        }

        // Check if the album with the given name exists
        boolean albumExists = albums.stream().anyMatch(album -> album.getName().equals(albumName));

        if (!albumExists) {
            return username + " doesn't have an album with the given name.";
        }

        // Check if the album is associated with any active player or search
        for (User user : Admin.getUsers()) {
            if (user.getPlayer().getSource() != null) {
                for (Album album : artist.getAlbums()) {
                    if (user.getPlayer().getSource().getAudioCollection() != null) {

                        // Check if the album is currently being played
                        if (user.getPlayer().getSource().getAudioCollection().getOwner().
                                equals(album.getOwner())) {
                            return username + " can't delete this album.";
                        }
                    }

                    // Check if any song from the album is currently being played
                    for (Song song : album.getSongs()) {
                        if (user.getPlayer().getSource().getAudioFile() != null
                                && user.getPlayer().getSource().getAudioFile().getName().
                                equals(song.getName())) {
                            return username + " can't delete this album.";
                        }
                    }
                }
            }
        }

        // Remove the album from the global list of albums and associated songs
        albums.removeAll(artist.getAlbums());
        Admin.getSongs().removeAll(artist.getAlbums());

        return username + " deleted the album successfully.";
    }


    private static boolean isUserArtist(final String username) {
        for (Artist artist : Admin.getArtists()) {
            if (artist.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
