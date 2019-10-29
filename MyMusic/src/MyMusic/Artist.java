package MyMusic;
import java.util.ArrayList;

public class Artist {
	private int id;
	private String name;
	private String imagePath;
	private float rating;
	private ArrayList<Track> tracks;

	Artist(String name, String imagePath, Float rating)
	{
		this.name = name;
		this.imagePath = imagePath;
		this.rating = rating;
		//this.tracks =tracks ;
	}
	
	public void addTrack(Track track)
	{
		tracks.add(track);
	}


	public int getId()
	{
		return this.id;
	}

	public String getName() { return this.name; }

	public String getImagePath()
	{
		return this.imagePath;
	}

	public float getRating() { return this.rating; }

	public ArrayList<Track> getTracks()
	{
		return this.tracks;
	}


	public void setId(int id)
	{
		this.id=id;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath=imagePath;
	}

	public void setRating(float rating)
	{
		this.rating=rating;
	}

}
