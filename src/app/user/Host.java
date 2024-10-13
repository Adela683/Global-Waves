package app.user;

import app.Admin;
import app.audio.Collections.Announcement;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.LibraryEntry;
import fileio.input.CommandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static app.Admin.getUser;
import static app.Admin.updateTimestamp;

public class Host extends LibraryEntry {

    @Setter
    @Getter
    private static List<Podcast> podcasts;

    @Setter
    @Getter
    private static List<Announcement> announcements;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String type = "host";


    public Host(final String name) {
        super(name);
        this.name = name;
        type = "host";
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }


    /**
     * Adds a new podcast for a specific host user.
     *
     * @param username  The username of the host adding the podcast.
     * @param timestamp The timestamp of the podcast creation (unused in the provided code).
     * @param name      The name of the new podcast.
     * @param episodes  List of episodes for the new podcast.
     * @return A message indicating the success or failure of adding a new podcast.
     */
    public static String addPodcast(final String username, final int timestamp,
                                    final String name, final List<Episode> episodes) {

        //verify if the username exists
        if (getUser(username) == null) {
            return "The username doesn't exist.";
        }

        //verify if the user is in the list of hosts
        if (!isUserHost(username)) {
            return username + " is not a host.";
        }

        //verify if the user has another podcast with the same name
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return username + " has another podcast with the same name.";
            }
        }

        //verify if the username has the same episode in this podcast
        for (Episode episode : episodes) {
            if (hasSameEpisode(name, episode)) {
                return username + " has the same episode in this podcast.";
            }
        }

        //add the podcast
        Podcast podcast = new Podcast(name, username, episodes);
        podcasts.add(podcast);
        Admin.addPodcast(podcast);
        getUser(username).getPodcasts().add(podcast);
        return username + " has added new podcast successfully.";
    }

    /**
     * Checks if a user is a host.
     *
     * @param username The username of the user to be checked.
     * @return True if the user is a host, false otherwise.
     */
    public static boolean isUserHost(final String username) {
        for (Host host : Admin.getHosts()) {
            if (host.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a podcast has an episode with the same name as the one to be added.
     *
     * @param podcastName   The name of the podcast to be checked.
     * @param episodeToCheck The episode to be checked.
     * @return True if the podcast has an episode with the same name, false otherwise.
     */
    public static boolean hasSameEpisode(final String podcastName, final Episode episodeToCheck) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(podcastName)) {
                for (Episode existingEpisode : podcast.getEpisodes()) {
                    if (existingEpisode.getName().equals(episodeToCheck.getName())) {
                        return true;
                    }
                }
                break; // Dacă am găsit podcastul, nu mai este nevoie să căutăm în continuare
            }
        }
        return false;
    }

    /**
     * Removes a podcast for a specific host user.
     *
     * @param username    The username of the host removing the podcast.
     * @param podcastName The name of the podcast to be removed.
     * @return A message indicating the success or failure of removing the podcast.
     */
    public static String removePodcast(final String username, final String podcastName) {
        // Verify if the username exists.
        if (!username.equals(username)) {
            return "The username " + username + " doesn't exist.";
        }

        // Verify if the user is in the list of hosts.
        if (!isUserHost(username)) {
            return username + " is not a host.";
        }

        // Verify if the user has another podcast with the same name.
        boolean hasPodcast = false;
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(podcastName)) {
                hasPodcast = true;
                break;
            }
        }

        // If the user doesn't have a podcast with the given name, return an error message.
        if (!hasPodcast) {
            return username + " doesn't have a podcast with the given name.";
        }

        // Check if the podcast is currently playing for any user.
        for (User user : Admin.getUsers()) {
            if (user.getPlayer().getSource() != null) {
                if (user.getPlayer().getSource().
                        getAudioCollection().getName().equals(podcastName)) {
                    return username + " can't delete this podcast.";
                }
            }
        }

        // Find and remove the podcast from the list of podcasts.
        Podcast podcastRemoved = null;
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(podcastName)) {
                podcastRemoved = podcast;
                break;
            }
        }
        podcasts.remove(podcastRemoved);
        Admin.removePodcast(podcastRemoved);

        // Return a success message.
        return username + " deleted the podcast successfully.";
    }

    /**
     * Adds a new announcement for a specific host user.
     *
     * @param username    The username of the host adding the announcement.
     * @param name        The name of the new announcement.
     * @param description The description of the new announcement.
     * @return A message indicating the success or failure of adding a new announcement.
     */
    public static String addAnnouncement(final String username,
                                         final String name, final String description) {
        //verify if the username exists
        if (!username.equals(username)) {
            return "The username doesn't exist.";
        }

        //verify if the user is in the list of hosts
        if (!isUserHost(username)) {
            return username + " is not a host.";
        }

        //verify if the user has already added an announcement with this name
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return username + " has already added an announcement with this name.";
            }
        }

        //add the announcement
        Announcement announcement = new Announcement(name, description);
        announcements.add(announcement);
        getUser(username).getAnnouncements().add(announcement);
        return username + " has successfully added new announcement.";
    }

    /**
     * Removes an announcement for a specific host user.
     *
     * @param username The username of the host removing the announcement.
     * @param name     The name of the announcement to be removed.
     * @return A message indicating the success or failure of removing the announcement.
     */
    public static String removeAnnouncement(final String username, final String name) {
        // Verify if the username exists.
        if (!username.equals(username)) {
            return "The username doesn't exist.";
        }

        // Verify if the user is in the list of hosts.
        if (!isUserHost(username)) {
            return username + " is not a host.";
        }


        // Verify if the user has no announcement with the given name.
        boolean hasAnnouncement = false;
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                hasAnnouncement = true;
                break;
            }
        }

        // If the user has no announcement with the given name, return an error message.
        if (!hasAnnouncement) {
            return username + " has no announcement with the given name.";
        }

        // Remove the announcement with the specified name.
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                announcements.remove(announcement);
                break; // Break after removing to avoid ConcurrentModificationException.
            }
        }

        // Return a success message.
        return username + " has successfully deleted the announcement.";
    }


}

