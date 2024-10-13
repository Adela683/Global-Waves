package app;

//import app.audio.Collections.Album;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Episode;
import app.player.PlayerStats;
import app.searchBar.Filters;
//import app.user.Artist;
import app.user.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import app.user.Host;
import fileio.input.EpisodeInput;


import java.util.ArrayList;
import java.util.List;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    public static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */

    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        ArrayList<String> results = null;

        if (user == null) {
            return null;
        }

        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
            results = new ArrayList<>();
        } else {


            Filters filters = new Filters(commandInput.getFilters());
            String type = commandInput.getType();

            results = user.search(filters, type);
            message = "Search returned " + results.size() + " results";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return null;
        }

        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return null;
        }
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return null;
        }

        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            return null;
        }

        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;

        if (user == null) {
            return null;
        }

        if (!user.isOnline()) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            message = user.like();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {

        if (Admin.getUser(commandInput.getUsername()) == null) {
            // Nu face nimic dacă obiectul User este null
            return null;
        }

        User user = Admin.getUser(commandInput.getUsername());

        if (commandInput.getPlaylistName() == null || commandInput.getPlaylistName().isEmpty()) {
            // Nu face nimic dacă numele playlist-ului este null sau gol
            return null;
        }

           String message = user.createPlaylist(commandInput.getPlaylistName(),
                   commandInput.getTimestamp());

           ObjectNode objectNode = objectMapper.createObjectNode();
           objectNode.put("command", commandInput.getCommand());
           objectNode.put("user", commandInput.getUsername());
           objectNode.put("timestamp", commandInput.getTimestamp());
           objectNode.put("message", message);

           return objectNode;
       }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            // Nu face nimic dacă obiectul User este null
            return null;
        }

        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            // Nu face nimic dacă obiectul User este null
            return null;
        }

        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            // Nu face nimic dacă obiectul User este null
            return null;
        }

        PlayerStats stats = user.getPlayerStats();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Switches the connection status (online/offline) for the specified user and creates
     * an ObjectNode with the result message for further communication.
     *
     * @param commandInput The CommandInput object containing information about the command.
     * @return An ObjectNode with information about the command execution, including the
     *         switched connection status message.
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {

        // Retrieve the user associated with the provided username
        User user = Admin.getUser(commandInput.getUsername());

        // Initialize the message variable
        String message = null;

        // Check if the user exists
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            // Switch the online status for the user and retrieve the status change message
            message = user.switchOnline(commandInput.getUsername());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);


        return objectNode;
    }

    /**
     * Retrieves a list of online users and creates an ObjectNode
     * with the result for further communication.
     *
     * @param commandInput The CommandInput object containing information
     *                    about the command.
     * @return An ObjectNode with information about the command execution,
     * including the list of online users as a result.
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        List<String> onlineUsers = Admin.getOnlineUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    /**
     * Adds a new user to the system based on the information provided
     * in the CommandInput object
     * @param commandInput The CommandInput object containing information
     * about the command,including the new user's details
     * (username, age, city, type).
     * @return An ObjectNode with information about the command execution,
     * including a message indicating whether the user addition was successful.
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Admin.addUsers(commandInput.getUsername(),
                commandInput.getAge(), commandInput.getCity(), commandInput.getType());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);


        return objectNode;
    }

    /**
     * Adds a new album to the system based on the information provided
     * in the CommandInput object.
     *
     * @param commandInput The CommandInput object containing information
     * about the command,including the album details
     *(username, name, release year, description, songs).
     * @return An ObjectNode with information about the command execution,
     * including a message indicating whether the album addition was successful.
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        // Retrieve the user associated with the provided username (if any)
        User user = Admin.getUser(commandInput.getUsername());

        // Attempt to add a new album with the provided details and capture the result message
        String message = Admin.addAlbum(
                commandInput.getUsername(),
                commandInput.getName(),
                commandInput.getReleaseYear(),
                commandInput.getDescription(),
                commandInput.getSongs()
        );

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }


    /**
     * Retrieves and formats the information about albums associated with a
     * specific user.
     * The result includes album names and their respective songs.
     *
     * @param commandInput The CommandInput object containing information
     * about the command , including the username for which albums are to
     * be displayed.
     * @return An ObjectNode with information about the command execution,
     * including a result containing formatted details of albums associated
     * with the specified user.
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {

        ObjectNode objectNode = objectMapper.createObjectNode();

        // Retrieve album details for the specified username
        var aux = Admin.showAlbums(commandInput.getUsername());

        // Convert the result to an ArrayNode
        ArrayNode rez = objectMapper.valueToTree(aux);

        // Format the ArrayNode to improve the structure of the result
        for (JsonNode o : rez) {
            if (o.isObject()) {
                ObjectNode obj = (ObjectNode) o;
                obj.set("name", obj.remove("a"));
                obj.set("songs", obj.remove("b"));
            }
        }

        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", rez);

        return objectNode;
    }


    /**
     * Retrieves and formats the information about albums
     * associated with a specific user.
     * The result includes album names and their respective songs.
     *
     * @param commandInput The CommandInput object containing information about
     * the command,including the username for which albums are to be displayed.
     * @return An ObjectNode with information about the command execution,
     * including a result containing formatted details of albums associated
     * with the specified user.
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        var aux = Admin.showPodcasts(commandInput.getUsername());
        ArrayNode rez = objectMapper.valueToTree(aux);
        for (JsonNode o : rez) {
            if (o.isObject()) {
                ObjectNode obj = (ObjectNode) o;
                obj.set("name", obj.remove("a"));
                obj.set("episodes", obj.remove("b"));
            }
        }
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", rez);

        return objectNode;
    }


    /**
     * Generates and returns a JSON ObjectNode containing information
     * to print the current page for a user.
     * @param commandInput The CommandInput object containing
     * user command details.
     * @return An ObjectNode containing user information, command,
     * timestamp, and the message to print on the current page.
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        // Retrieve the user object based on the provided username.
        User user = Admin.getUser(commandInput.getUsername());

        // Create a new ObjectMapper and an ObjectNode to store the JSON information.
        ObjectNode objectNode = objectMapper.createObjectNode();

        // Initialize a string to store the final message to be printed on the page.
        String rezPrint = "";

        // Check the current page and generate the appropriate message.
        if (Admin.currentPage.equals("Home")) {
            // If the current page is "Home," retrieve liked songs and
            // followed playlists for the user.
            String likedSongs = Admin.getLikedSongs(commandInput.getUsername(),
                    false);
            String followedPlaylists = Admin.getFollowedPlaylists(commandInput.getUsername(),
                    false);
            rezPrint = likedSongs + "\n\n" + followedPlaylists;
        } else if (Admin.currentPage.equals("LikedContent")) {
            // If the current page is "LikedContent," retrieve liked songs and
            // followed playlists with detailed information.
            String likedSongs = Admin.getLikedSongs(commandInput.getUsername(),
                    true);
            String followedPlaylists = Admin.getFollowedPlaylists(commandInput.getUsername(), true);
            rezPrint = likedSongs + "\n\n" + followedPlaylists;
        } else if (Admin.currentPage.equals("host")) {
            // If the current page is "host," retrieve podcasts based on
            // the user's last search type.
            rezPrint = Admin.getPodcasts(commandInput.getUsername(),
                    user.getSearchBar().getLastSearchType());
        } else if (Admin.currentPage.equals("artist")) {
            // If the current page is "artist," retrieve information
            // for the selected artist.
            rezPrint = Admin.getArtistPage(Admin.searchSelection);
        }

        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());

        // Check if the user is online and add the appropriate message to the ObjectNode.
        if (user.isOnline()) {
            objectNode.put("message", rezPrint);
        } else {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        }

        return objectNode;
    }


    /**
     * Adds merchandise to the inventory of a specific user (artist).
     *
     * @param commandInput The CommandInput object containing information
     * about the command, including the username of the artist, merchandise
     * name, description, and price.
     * @return An ObjectNode with information about the command execution,
     * including a message indicating the success or failure of the merchandise
     * addition.
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Admin.addMerch(commandInput.getUsername(),
                commandInput.getName(), commandInput.getDescription(),
                commandInput.getPrice());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Adds a new event to the schedule of a specific user (artist).
     *
     * @param commandInput The CommandInput object containing information
     * about the command , including the username of the artist, event name,
     * description, and date.
     * @return An ObjectNode with information about the command execution,
     * including a message indicating the success or failure of the event addition.
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = Admin.addEvent(commandInput.getUsername(),
                commandInput.getName(), commandInput.getDescription(),
                commandInput.getDate());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Retrieves a list of all users in the system and returns the
     * result as an ObjectNode.
     *
     * @param commandInput The CommandInput object containing
     * information about the command, including the username of the requesting user.
     * @return An ObjectNode with information about the command execution, including
     * a list of all users.
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
         List<String> result = Admin.getAllUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(result));

        return objectNode;
    }


    /**
     * Adds a new podcast to the system with the provided information.
     *
     * @param commandInput The CommandInput object containing information
     * about the command, including the username, timestamp, name, and episodes
     * of the podcast.
     * @return An ObjectNode with information about the command execution,
     * including a message
     * indicating the success or failure of the podcast addition.
     */
        public static ObjectNode addPodcast(final CommandInput commandInput) {

            User user = Admin.getUser(commandInput.getUsername());
            ArrayList<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episode : commandInput.getEpisodes()) {
                episodes.add(new Episode(episode.getName(),
                        episode.getDuration(), episode.getDescription()));
            }

            String message = Host.addPodcast(commandInput.getUsername(),
                    commandInput.getTimestamp(), commandInput.getName(), episodes);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }

    /**
     * Adds a new podcast to the system with the provided information.
     *
     * @param commandInput The CommandInput object containing information
     * about the command, including the username, timestamp, name, and episodes
     * of the podcast.
     * @return An ObjectNode with information about the command execution,
     * including a message
     *  indicating the success or failure of the podcast addition.
     */
        public static ObjectNode removePodcast(final CommandInput commandInput) {
            User user = Admin.getUser(commandInput.getUsername());
            String message = Host.removePodcast(commandInput.getUsername(), commandInput.getName());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }

    /**
     * Retrieves the names of the top 5 albums based on the total
     * number of likes and alphabetical order.
     *
     * @param commandInput The CommandInput object containing
     * information about the command, including the timestamp.
     * @return An ObjectNode with information about the command execution,
     * including the names
     * of the top 5 albums based on likes and alphabetical order.
     */
        public static ObjectNode getTop5Albums(final CommandInput commandInput) {
            List<String> albums = Admin.getTop5Albums();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("result", objectMapper.valueToTree(albums));

            return objectNode;
        }

    /**
     * Adds a new announcement to the system by a host.
     *
     * @param commandInput The CommandInput object containing information
     * about the command, including the timestamp, username, announcement name,
     * and description.
     * @return An ObjectNode with information about the command execution,
     * including a message
     * indicating the success or failure of the announcement addition.
     */
        public static ObjectNode addAnnouncement(final CommandInput commandInput) {
            User user = Admin.getUser(commandInput.getUsername());
            String message = Host.addAnnouncement(commandInput.getUsername(),
                    commandInput.getName(), commandInput.getDescription());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }

    /**
     * Removes an announcement from the system by a host.
     *
     * @param commandInput The CommandInput object containing information
     * about the command,including the timestamp, username, and announcement
     * name to be removed.
     * @return An ObjectNode with information about the command execution,
     * including a message
     * indicating the success or failure of the announcement removal.
     */
        public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
            User user = Admin.getUser(commandInput.getUsername());
            String message = Host.removeAnnouncement(commandInput.getUsername(),
                    commandInput.getName());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }

    /**
     * Changes the current page for a user based on the command input.
     *
     * @param commandInput The command input containing information about the page change.
     * @return An ObjectNode containing information about the command execution.
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        // Get the next page from the command input.
        String nextPage = commandInput.getNextPage();
        String message = "";

        // Check if the next page is "Home" or "LikedContent".
        if (nextPage.equals("Home") || nextPage.equals("LikedContent")) {
            // Update the current page for the user.
            Admin.currentPage = nextPage;
            message = commandInput.getUsername() + " accessed " + nextPage + " successfully.";
        } else {
            // The user is trying to access a non-existent page.
            message = commandInput.getUsername() + " is trying to access a non-existent page.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }


    /**
     * Deletes a user, artist, or host from the system based on the provided
     * information.
     *
     * @param commandInput The CommandInput object containing information about
     * the command,including the timestamp, username, and user type to be deleted.
     * @return An ObjectNode with information about the command execution,
     * including a message
     * indicating the success or failure of the user deletion.
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
            User user = Admin.getUser(commandInput.getUsername());
            String message = Admin.deleteUser(commandInput.getUsername(), commandInput.getType());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }


    /**
     * Removes an album associated with a specific artist from the system.
     *
     * @param commandInput The CommandInput object containing information about
     * the command, including the timestamp, username, and the name of the album
     * to be removed.
     * @return An ObjectNode with information about the command execution,
     * including a message indicating the success or failure of the album removal.
     */
        public static ObjectNode removeAlbum(final CommandInput commandInput) {
            User user = Admin.getUser(commandInput.getUsername());
            String message = Admin.getArtist(commandInput.getUsername()).removeAlbum(commandInput.getUsername(), commandInput.getName());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }

}
