
public class Track {

	private String name;
	private int id;
	private double duration;
	private int num_plays;
	private int album_id;

	public Track(String name, int album_id,double time, int num_plays)
	{

		this.name=name;
		this.album_id=album_id;
		this.duration=time;
		this.num_plays=num_plays;
	}
	public int getId()
	{
		return id;
	}


	public String getName()
	{
		return name;
	}

	public int getAlbumId()
	{
		return this.album_id;
	}

	public double getDuration()
	{
		return this.duration;
	}

	public int getNumPlays()
	{
		return this.num_plays;
	}


	public void setId(int id)
	{
		this.id=id;
	}
	public void setName(String name)
	{
		this.name=name;
	}

	public void setAlbumId(int album_id)
	{
		this.album_id=album_id;
	}

	public void setTime(int duration)
	{
		this.duration=duration;
	}

	public void setNumPlays(int num_plays)
	{
		 this.num_plays=num_plays;
	}
}
