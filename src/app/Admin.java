package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Collections.Merchandise;
import app.audio.Collections.Announcement;
import app.audio.Collections.Event;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Admin.
 */
public final class Admin {

    @Getter
    private static List<User> users = new ArrayList<>();
    private static ArrayList<Song> songs = new ArrayList<>();

    @Getter
    private static List<Album> albums = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();

    @Getter
    private static List<Artist> artists = new ArrayList<>();

    @Getter
    private static List<Host> hosts = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;
    public static String searchSelection = "";

    public static String currentPage = "Home";


    private Admin() {
    }

    //get instance singleton method
    private static Admin instance = null;

    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }
    /**
     * Sets users.
     *
     * @param username the username of the user
     */
    public static List<Album> getSpecAlbum(final String username) {
       return getArtist(username).getAlbums();
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }


    /**
     * Sets songs.
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }


    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                        episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public static List<Artist> getArtist() {
        return new ArrayList<>(artists);
    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public static List<Host> getHost() {
        return new ArrayList<>(hosts);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }


    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets song.
     *
     * @param username the username
     * @return artist
     */
    public static Artist getArtist(final String username) {
        for (Artist user : artists) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets song.
     *
     * @param username the username
     * @return host
     */
    public static Host getHost(final String username) {
        for (Host user : hosts) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.isOnline()) {
                user.simulateTime(elapsed);
            }
        }
    }

    public static int getTimestamp () {
        return timestamp;
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        hosts = new ArrayList<>();
        artists = new ArrayList<>();
        ArrayList<Announcement> announcements = new ArrayList<>();
        currentPage = "Home";
        timestamp = 0;
    }

    /**
     * Retrieves a list of usernames for online users of type "user".
     * @return A List of String containing the usernames of online users.
     */
    public static List<String> getOnlineUsers() {
        // Create a List to store the usernames of online users.
        List<String> userOnline = new ArrayList<>();

        // Iterate through the list of users.
        for (User user : users) {
            // Check if the user is online and has the type "user".
            if (user.isOnline() && user.getType().equals("user")) {
                // If the conditions are met, add the username to the List.
                userOnline.add(user.getUsername());
            }
        }

        // Return the List containing the usernames of online users.
        return userOnline;
    }

    /**
     * Adds a new user with the specified information and updates corresponding
     * lists for artists and hosts.
     * @param username The username of the new user.
     * @param age The age of the new user.
     * @param city The city of the new user.
     * @param type The type of the new user (e.g., "user", "artist", "host").
     * @return A message indicating the success or failure of the operation.
     */
    public static String addUsers(final String username, final int age, final String city,
                                  final String type) {
        // Iterate through the existing users to check if the username is already taken.
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                // If the username is already taken, return an error message.
                return "The username " + username + " is already taken.";
            }
        }

        // If the username is not taken, create a new User and add it to the users list.
        users.add(new User(username, age, city, type));

        // If the user is of type "artist", add them to the list of artists.
        if (type.equals("artist")) {
            artists.add(new Artist(username));
        }

        // If the user is of type "host", add them to the list of hosts.
        if (type.equals("host")) {
            hosts.add(new Host(username));
        }

        // Return a success message indicating that the user has been added successfully.
        return "The username " + username + " has been added successfully.";
    }


    /**
     * Retrieves a list of albums owned by a specific user, along with the songs in each album.
     * @param username The username of the user for whom to retrieve albums.
     * @return A List of Pair objects containing album names and lists of songs for each album.
     */
    public static List<Pair<String, List<String>>> showAlbums(final String username) {
        // Create a list to store pairs of album names and lists of songs.
        List<Pair<String, List<String>>> result = new ArrayList<>();

        // Iterate through each album in the global list of albums.
        for (Album album : albums) {
            // Create a list to store song names for the current album.
            List<String> songsInAlbum = new ArrayList<>();

            // Check if the owner of the current album matches the specified username.
            if (album.getOwner().equals(username)) {
                // Iterate through each song in the global list of songs.
                for (Song song : Admin.songs) {
                    // Check if the current song belongs to the current album.
                    if (song.getAlbum().equals(album.getName())) {
                        // Add the name of the song to the list of songs for the current album.
                        songsInAlbum.add(song.getName());
                    }
                }

                // Add a Pair containing the album name and the list of songs to the result list.
                result.add(new Pair<>(album.getName(), songsInAlbum));
            }
        }

        // Return the final list of album-song pairs.
        return result;
    }

    /**
     * Retrieves a list of podcasts owned by a specific user, along with the names
     * of episodes in each podcast.
     * @param username The username of the user for whom to retrieve podcasts.
     * @return A List of Pair objects containing podcast names and lists of episode
     * names for each podcast.
     */
    public static List<Pair<String, List<String>>> showPodcasts(final String username) {
        // Create a list to store pairs of podcast names and lists of episode names.
        List<Pair<String, List<String>>> result = new ArrayList<>();

        // Iterate through each podcast in the global list of podcasts.
        for (Podcast podcast : podcasts) {
            // Create a list to store episode names for the current podcast.
            List<String> episodes = new ArrayList<>();

            // Check if the owner of the current podcast matches the specified username.
            if (podcast.getOwner().equals(username)) {
                // Iterate through each episode in the list of episodes for the current podcast.
                for (Episode episode : podcast.getEpisodes()) {
                    // Add the name of the episode to the list of episodes for the current podcast.
                    episodes.add(episode.getName());
                }

                // Add a Pair containing the podcast name and the list of episode
                // names to the result list.
                result.add(new Pair<>(podcast.getName(), episodes));
            }
        }

        // Return the final list of podcast-episode pairs.
        return result;
    }


    /**
     * Retrieves a list of playlists owned by a specific user,
     * along with the names of songs in each playlist.
     * @param username The username of the user for whom to retrieve playlists.
     * @return A List of Pair objects containing playlist
     * names and lists of song names for each playlist.
     */
    public static String addAlbum(final String username, final String name, final int releaseYear,
                                  final String description, final List<SongInput> albumSongs) {

        if (getUser(username) == null) {
            return "The username " + username + " does not exist!";
        }
        if (!getUser(username).getType().equals("artist")) {
            return username + " is not an artist.";
        }
        Artist artist = getArtist(username);

        // 1. <username> has the same album name
        for (Album album : albums) {
            // Verifică dacă albumul curent are un nume și este același cu noul album
            if (album.getName().equals(name)) {
                return username + " has another album with the same name.";
            }
        }


        for (int i = 0; i < albumSongs.size() - 1; i++) {
            for (int j = i + 1; j < albumSongs.size(); j++) {
                if (albumSongs.get(j).getName().equals(albumSongs.get(i).getName())) {
                    return username + " has the same song at least twice in this album.";
                }
            }
        }

        // create the list of songs for the album type Song not SongInput
        ArrayList<Song> songs2 = new ArrayList<>();
        for (SongInput song : albumSongs) {
            if (!songs.contains(song)) {
                Song newSong = new Song(song.getName(), song.getDuration(), song.getAlbum(),
                        song.getTags(), song.getLyrics(), song.getGenre(), song.getReleaseYear(),
                        song.getArtist());
                Song newSong2 = new Song(song.getName(), song.getDuration(), song.getAlbum(),
                        song.getTags(), song.getLyrics(), song.getGenre(), song.getReleaseYear(),
                        song.getArtist());
                songs.add(newSong);
                songs2.add(newSong2);
            }
        }


        //albums.add(new Album(name, username, releaseYear, description, songs));
        Admin.albums.add(new Album(name, username, releaseYear, description, songs));
        getArtist(username).addAlbum(new Album(name, username, releaseYear, description, songs2));
        //Admin.getAlbums().add(new Album(name, username, releaseYear, description,songs));
        return username + " has added new album successfully.";

    }

    /**
     * Generates a string containing the list of liked songs for a specific user.
     * @param username The username of the user for whom to retrieve liked songs.
     * @param needsArtist A flag indicating whether to include artist information
     *                     along with song names.
     * @return A string containing the list of liked songs for the user.
     */
    public static String getLikedSongs(final String username, final boolean needsArtist) {
        // Initialize the result string with a header.
        String s = "Liked songs:\n\t[";

        // Iterate through each user in the global list of users.
        for (User u : users) {
            // Check if the current user's username matches the specified username.
            if (u.getUsername().equals(username)) {
                // Iterate through each liked song in the user's list of liked songs.
                for (int i = 0; i < u.getLikedSongs().size(); ++i) {
                    // Add the name of the liked song to the result string.
                    s += u.getLikedSongs().get(i).getName();

                    // Check if additional artist information is requested.
                    if (needsArtist) {
                        // If yes, add the artist information to the result string.
                        s += " - " + u.getLikedSongs().get(i).getArtist();
                    }

                    // Add a comma if there are more liked songs to include.
                    if (i < u.getLikedSongs().size() - 1) {
                        s += ",";
                    }
                }

                // Break the loop after processing the user's liked songs.
                break;
            }
        }

        // Close the list of liked songs in the result string.
        s += "]";

        // Return the final string containing the list of liked songs.
        return s;
    }


    /**
     * Generates a string containing the list of followed artists for a specific user.
     * @param username The username of the user for whom to retrieve followed artists.
     * @return A string containing the list of followed artists for the user.
     */
    public static String getFollowedPlaylists(final String username, final boolean needsArtist) {
        String s = "Followed playlists:\n\t[";
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                for (int i = 0; i < u.getFollowedPlaylists().size(); ++i) {
                    s += u.getFollowedPlaylists().get(i).getName();
                    if (needsArtist) {
                        s += " - " + u.getFollowedPlaylists().get(i).getOwner();
                    }
                    if (i < u.getFollowedPlaylists().size() - 1) {
                        s += ",";
                    }
                }
            }
        }
        s += "]";
        return s;
    }


    /**
     * Adds new merchandise for a specific artist.
     * @param username The username of the artist adding merchandise.
     * @param name The name of the new merchandise.
     * @param description The description of the new merchandise.
     * @param price The price of the new merchandise.
     * @return A message indicating the success or failure of adding new merchandise.
     */
    public static String addMerch(final String username, final String name,
                                  final String description, final int price) {

        //verify is the user exists
        Artist artist = getArtist(username);
        if (artist == null) {
            return "The username " + username + " doesn't exist.";
        }

        if (!getUser(username).getType().equals("artist")) {
            return "The username " + username + " is not an artist!";
        }

        //verify if the user has merchandise with the same name
        for (Merchandise merch : artist.getMerchandise()) {
            if (merch.getName().equals(name)) {
                return username + " has merchandise with the same name.";
            }
        }

        //verify if the price for merchandise is positive
        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }

        //add the merchandise
        Merchandise newMerch = new Merchandise(name, description, price);
        artist.addMerchandise(newMerch);

        return username + " has added new merchandise successfully.";


    }

    /**
     * Adds a new event for a specific artist.
     *
     * @param username    The username of the artist adding the event.
     * @param name        The name of the new event.
     * @param description The description of the new event.
     * @param date        The date of the new event (format: "YYYY-MM-DD").
     * @return A message indicating the success or failure of adding a new event.
     */
    public static String addEvent(final String username, final String name,
                                  final String description, final String date) {

        //verify is the user exists
        if (getUser(username) == null) {
            return "The username " + username + " doesn't exist.";
        }
        String userType = getUser(username).getType();
        if (userType == null || !userType.equals("artist")) {
            return username + " is not an artist.";
        }
        Artist artist = getArtist(username);
        for (Event event : artist.getEvents()) {
            if (event.getName().equals(name)) {
                return username + " has another event with the same name.";
            }
        }

        final int number = 10;
        //verify if the date is valid
        if (date.length() != number) {
            return "Event for" + username + " does not have a valid date.";
        }

        //add the event
        Event newEvent = new Event(name, description, date);
        artist.addEvent(newEvent);

        return username + " has added new event successfully.";
    }



    /**
     * Retrieves a list of all usernames based on user types (user, artist, host).
     *
     * @return A list containing all usernames of users, artists, and hosts.
     */
    public static List<String> getAllUsers() {
        // Initialize a list to store all usernames.
        List<String> allUsernames = new ArrayList<>();

        // Iterate through users and add usernames of type "user" to the list.
        for (User user : users) {
            // Check if getType() is not null before calling equals.
            if (user.getType() != null && user.getType().equals("user")) {
                allUsernames.add(user.getUsername());
            }
        }

        // Iterate through users and add usernames of type "artist" to the list.
        for (User user : users) {
            // Check if getType() is not null before calling equals.
            if (user.getType() != null && user.getType().equals("artist")) {
                allUsernames.add(user.getUsername());
            }
        }

        // Iterate through users and add usernames of type "host" to the list.
        for (User user : users) {
            // Check if getType() is not null before calling equals.
            if (user.getType() != null && user.getType().equals("host")) {
                allUsernames.add(user.getUsername());
            }
        }

        // Return the final list containing all usernames.
        return allUsernames;
    }

    /**
     * Adds a new podcast for a specific user.
     *
     * @param podcast The podcast to be added.
     */
    public static void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Removes a podcast from the global list of podcasts.
     *
     * @param podcast The podcast to be removed.
     */
    public static void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Retrieves a list of names for the top 5 albums based on total likes and name.
     *
     * @return A list containing names of the top 5 albums.
     */
    public static List<String> getTop5Albums() {

        // Use Java Streams to sort albums based on total likes
        // (in descending order) and then by name.
        final int max = 5;
        List<Album> sortedAlbums = albums.stream()
                .sorted(Comparator.comparing(Album::getTotalLikes).reversed()
                        .thenComparing(Album::getName))
                .limit(max) // Limit the result to the top 5 albums.
                .collect(Collectors.toList());

        // Extract the names of the sorted albums and return as a list.
        return sortedAlbums.stream()
                .map(Album::getName)
                .collect(Collectors.toList());
    }


    /**
     * Generates a string containing information about podcasts and announcements
     * for a specific user.
     * @param username The username of the user requesting the information.
     * @param whose The username for whom to retrieve podcast and announcement information.
     * @return A string containing information about podcasts and announcements for
     * the specified user.
     */
    public static String getPodcasts(final String username, final String whose) {
        // Initialize the result string with a header for podcasts.
        String rez = "Podcasts:\n\t[";

        // Retrieve the User object based on the specified username.
        User u = getUser(whose);

        // Check if the user exists.
        if (u != null) {
            // Iterate through each podcast in the user's list of podcasts.
            for (int i = 0; i < u.getPodcasts().size(); ++i) {
                Podcast p = u.getPodcasts().get(i);

                // Check if the owner of the podcast matches the user's username.
                if (p.getOwner().equals(u.getUsername())) {
                    // Add the name of the podcast to the result string.
                    rez += p.getName() + ":\n\t[";

                    // Iterate through each episode in the podcast.
                    for (int j = 0; j < p.getEpisodes().size(); j++) {
                        Episode e = p.getEpisodes().get(j);

                        // Add the name and description of each episode to the result string.
                        rez += e.getName() + " - " + e.getDescription();

                        // Add a comma if there are more episodes to include.
                        if (j < p.getEpisodes().size() - 1) {
                            rez += ", ";
                        }
                    }

                    // Close the list of episodes for the current podcast.
                    rez += "]\n";

                    // Add a comma if there are more podcasts to include.
                    if (i < u.getPodcasts().size() - 1) {
                        rez += ", ";
                    }
                }
            }

            // Start a new section for announcements in the result string.
            rez += "]\n\nAnnouncements:\n\t[";

            // Iterate through each announcement in the user's list of announcements.
            for (int i = 0; i < u.getAnnouncements().size(); ++i) {
                Announcement a = u.getAnnouncements().get(i);

                // Add the name and description of each announcement to the result string.
                rez += a.getName() + ":\n\t" + a.getDescription() + "\n";

                // Add a comma if there are more announcements to include.
                if (i < u.getAnnouncements().size() - 1) {
                    rez += ",";
                }
            }
        }

        // Close the list of announcements in the result string.
        return rez + "]";
    }


    /**
     * Generates a string containing information about albums, merchandise,
     * and events for a specific artist.
     * @param username The username of the artist for whom to retrieve information.
     * @return A string containing information about albums, merchandise,
     * and events for the specified artist.
     */
    public static String getArtistPage(final String username) {
        // Initialize the result string with a header for albums.
        String rez = "Albums:\n\t[";

        // Iterate through each artist in the global list of artists.
        for (Artist a : artists) {
            // Check if the current artist's name matches the specified username.
            if (a.getName().equals(username)) {
                // Iterate through each album in the artist's list of albums.
                for (int i = 0; i < a.getAlbums().size(); ++i) {
                    Album al = a.getAlbums().get(i);

                    // Add the name of the album to the result string.
                    rez += al.getName();

                    // Add a comma if there are more albums to include.
                    if (i < a.getAlbums().size() - 1) {
                        rez += ",";
                    }
                }
            }
            // Break the loop after processing the artist's albums.
            break;
        }

        // Close the list of albums in the result string.
        rez += "]\n\nMerch:\n\t[";

        // Iterate through each artist in the global list of artists (for merchandise).
        for (Artist a : artists) {
            // Check if the current artist's name matches the specified username.
            if (a.getName().equals(username)) {
                // Iterate through each merchandise item in the artist's list of merchandise.
                for (int i = 0; i < a.getMerchandise().size(); ++i) {
                    Merchandise m = a.getMerchandise().get(i);

                    // Add the name, price, and description of each merchandise item
                    // to the result string.
                    rez += m.getName() + " - " + m.getPrice() + ":\n\t" + m.getDescription();

                    // Add a comma if there are more merchandise items to include.
                    if (i < a.getMerchandise().size() - 1) {
                        rez += ", ";
                    }
                }
            }
        }

        // Close the list of merchandise in the result string.
        rez += "]\n\nEvents:\n\t[";

        // Iterate through each artist in the global list of artists (for events).
        for (Artist a : artists) {
            // Check if the current artist's name matches the specified username.
            if (a.getName().equals(username)) {
                // Iterate through each event in the artist's list of events.
                for (int i = 0; i < a.getEvents().size(); ++i) {
                    Event e = a.getEvents().get(i);

                    // Add the name, date, and description of each event to the result string.
                    rez += e.getName() + " - " + e.getDate() + ":\n\t" + e.getDescription();

                    // Add a comma if there are more events to include.
                    if (i < a.getEvents().size() - 1) {
                        rez += ", ";
                    }
                }
            }
        }

        // Close the list of events in the result string.
        return rez + "]";
    }


    /**
     * Deletes a user based on the provided username and type.
     *
     * @param username The username of the user to be deleted.
     * @param type     The type of the user (artist, host, user).
     * @return A message indicating the success or failure of the deletion operation.
     */
    public static String deleteUser(final String username, final String type) {

        // Check if the user with the given username exists
        if (getUser(username) != null) {

            // If the user is an artist
            if (getUser(username).getType().equals("artist")) {
                Artist artist = getArtist(username);

                // Check if the artist is associated with any active player or search
                for (User user : users) {
                    if (user.getSearchBar().getLastSearchType() != null
                            && user.getSearchBar().getLastSearchType().equals(username)) {
                        return username + " can't be deleted.";
                    }

                    if (user.getPlayer().getSource() != null) {
                        for (Album album : artist.getAlbums()) {
                            if (user.getPlayer().getSource().getAudioCollection() != null
                                    && user.getPlayer().getSource().getAudioCollection().getName().
                                            equals(album.getName())) {
                                return username + " can't be deleted.";
                            }

                            for (Song song : album.getSongs()) {
                                if (user.getPlayer().getSource().getAudioFile() != null
                                        && user.getPlayer().getSource().getAudioFile().getName().
                                                equals(song.getName())) {
                                    return username + " can't be deleted.";
                                }
                            }
                        }
                    }
                }

                // Decrease followers count for playlists followed by users
                for (Playlist playlist : getUser(username).getFollowedPlaylists()) {
                    playlist.decreaseFollowers();
                }

                // Remove albums and associated songs
                albums.removeAll(artist.getAlbums());
                for (Album album : artist.getAlbums()) {
                    songs.removeAll(album.getSongs());
                }

                // Remove songs associated with the artist
                songs.removeIf(song -> song.getArtist().equals(artist.getName()));

                // Remove artist from users' liked songs and followed playlists
                for (User u : users) {
                    u.getLikedSongs().removeIf(song -> song.getArtist().equals(artist.getName()));
                    u.getFollowedPlaylists().removeIf(playlist -> playlist.getOwner().
                            equals(artist.getName()));
                }

                // Remove the artist
                artists.remove(artist);

                return username + " was successfully deleted.";
            }

            // If the user is a host
            if (getUser(username).getType().equals("host")) {
                Host host = getHost(username);

                // Check if the host is associated with any active player or search
                for (User user : users) {
                    if (user.getSearchBar().getLastSearchType() != null
                            && user.getSearchBar().getLastSearchType().equals(username)) {
                        return username + " can't be deleted.";
                    }

                    if (user.getPlayer().getSource() != null) {
                        for (Podcast podcast : host.getPodcasts()) {
                            if (user.getPlayer().getSource().getAudioCollection() != null
                                    && user.getPlayer().getSource().getAudioCollection().
                                            getName().equals(podcast.getName())) {
                                return username + " can't delete this podcast.";
                            }
                        }
                    }
                }

                // Remove podcasts associated with the host
                podcasts.removeAll(host.getPodcasts());

                // Remove the host
                hosts.remove(host);

                return username + " was successfully deleted.";
            }

            // If the user is a regular user
            User user = getUser(username);

            // Check if the user is associated with any active player or search
            for (User u : users) {
                if (u.getSearchBar().getLastSearchType() != null
                        && u.getSearchBar().getLastSearchType().equals(username)) {
                    return username + " can't be deleted.";
                }

                if (u.getPlayer().getSource() != null) {
                    for (Playlist playlist : user.getPlaylists()) {
                        if (u.getPlayer().getSource().getAudioCollection() != null
                                && u.getPlayer().getSource().getAudioCollection().getName().
                                        equals(playlist.getName())) {
                            return username + " can't be deleted.";
                        }
                    }
                }
            }

            // Decrease followers count for playlists followed by users
            for (Playlist playlist : getUser(username).getFollowedPlaylists()) {
                playlist.decreaseFollowers();
            }

            // Remove the user from other users' followed playlists
            for (User u : users) {
                u.getFollowedPlaylists().removeIf(playlist -> playlist.getOwner().
                        equals(username));
            }

            // Remove the user
            users.remove(user);

            return username + " was successfully deleted.";
        }

        // Return an empty string if the user doesn't exist
        return "";
    }

}
