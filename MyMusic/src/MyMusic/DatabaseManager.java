package MyMusic;

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
		//Track t=new Track("Stairway to heaven", 4, 1.31,11);
		//d.addTrack(t);
		//Album album=new Album("Lootera",2016,"pop","",(float)8.21);
		//d.addAlbum(album);
		Artist artist=new Artist("Amit Trivedi","/users/downloads/img/amit.jpeg");
		d.addArtist(artist);

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
		PreparedStatement myStmt=null;

		try
		{
			myStmt=myConn.prepareStatement("insert into artists (artist_name, image_link) values (?,?)");
			myStmt.setString(1,artist.getName());
			myStmt.setString(2,artist.getImagePath());
			
			myStmt.executeUpdate();
			System.out.println("artist added successfully");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}
	
	public void addAlbum(Album album)
	{
		PreparedStatement myStmt=null;
		try
		{
			myStmt=myConn.prepareStatement("insert into albums (album_name,genre,year,image_path,rating) values (?,?,?,?,?)");
			myStmt.setString(1, album.getName());
			myStmt.setString(2, album.getGenre());
			myStmt.setInt(3,album.getYear());
			myStmt.setString(4,album.getImagePath());
			myStmt.setFloat(5,album.getRating());
			myStmt.executeUpdate();
			System.out.println("Album added successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}




	public User getUser(String username, String password) {
		try {
			// Login SQL Query
			PreparedStatement loginStmt = myConn.prepareStatement("Select * FROM User WHERE username = ?  AND password = ?");
			loginStmt.setString(1, username);
			loginStmt.setString(2, password);

			ResultSet rs = loginStmt.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				return new User(name, username, password);
			}
			else {
				// Result set is empty. Therefore, a user with the given username and password does not exist in the database.
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
