package MyMusic;

import java.util.ArrayList;

public class Playlist {
    private int id;
    private String name;
    private String imagePath;
    private ArrayList<Track> tracks;

    public Playlist(int id, String name, String imagePath, ArrayList<Track> tracks) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.tracks = tracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public void setTracks(ArrayList<Track> trackList) {
        tracks = trackList;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void removeTrack(int index) {
        tracks.remove(index);
    }
}
