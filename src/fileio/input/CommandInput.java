package fileio.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public final class CommandInput {
    @Setter
    @Getter
    private String command;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private Integer timestamp;
    @Setter
    @Getter
    private String type; // song / playlist / podcast
    @Setter
    @Getter
    private FiltersInput filters; // pentru search
    @Setter
    @Getter
    private Integer itemNumber; // pentru select
    @Setter
    @Getter
    private Integer repeatMode; // pentru repeat
    @Setter
    @Getter
    private Integer playlistId; // pentru add/remove song
    @Setter
    @Getter
    private String playlistName; // pentru create playlist
    @Setter
    @Getter
    private Integer seed; // pentru shuffle
    @Setter
    @Getter
    private Integer age;
    @Setter
    @Getter
    private String city;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private Integer releaseYear;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private List<SongInput> songs;
    @Setter
    @Getter
    private String date;
    @Setter
    @Getter
    private Integer price;
    @Setter
    @Getter
    private String nextPage;
    @Setter
    @Getter
    private List<EpisodeInput> episodes;

    public CommandInput() {
    }


    @Override
    public String toString() {
        return "CommandInput{"
                + "command='" + command + '\''
                + ", username='" + username + '\''
                + ", timestamp=" + timestamp
                + ", type='" + type + '\''
                + ", filters=" + filters
                + ", itemNumber=" + itemNumber
                + ", repeatMode=" + repeatMode
                + ", playlistId=" + playlistId
                + ", playlistName='" + playlistName + '\''
                + ", seed=" + seed
                + '}';
    }

}
