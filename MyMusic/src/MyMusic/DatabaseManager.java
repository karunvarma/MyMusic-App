import java.sql.*;

public class DatabaseManager {

	private Connection myConn;
	public DatabaseManager() throws Exception
	{

		String db_name="MyMusic";
		String dbUrl="jdbc:mysql://localhost:8889/"+db_name+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user="root";
		String password="root";
		myConn=DriverManager.getConnection(dbUrl,user,password);
		System.out.println("Database connected sucessfully");
	}

	public static void main(String args[])throws Exception
	{
		DatabaseManager d=new DatabaseManager();
		Track t=new Track("Stairway to heaven", 4, 1.31,11);
		d.addTrack(t);

	}

	public void getAllTracks() {}

	public void addTrack(Track track)
	{
		PreparedStatement myStmt=null;

		try
		{
			myStmt=myConn.prepareStatement("insert into tracks(track_name, album_id, time, num_plays) values (?,?,?,?)");
			myStmt.setString(1,track.getName());
			myStmt.setInt(2,track.getAlbumId());
			myStmt.setDouble(3,track.getDuration());
			myStmt.setInt(4,track.getNumPlays());
			myStmt.executeUpdate();
			System.out.println("track added successfully");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public void addArtist(Artist artist)
	{

	}
}
