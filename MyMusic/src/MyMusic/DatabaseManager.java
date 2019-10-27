package MyMusic;

import java.sql.*;
import java.util.ArrayList;

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
		ArrayList<Artist> artists=d.searchArtists("tri");
		ArrayList<Album> albums=d.searchAlbums("ter");
		ArrayList<Track> tracks=d.searchTracks("y");
		ArrayList<Track> allTracks=d.getAllTracks();
		ArrayList<Album> allAlbums=d.getAllAlbums();


	}

	public ArrayList<Track> getAllTracks() {		// to display latest tracks on the homepage

		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Track> tracks=new ArrayList<>();
		try
		{
			myStmt=myConn.prepareStatement("SELECT * FROM tracks INNER JOIN albums on tracks.album_id=albums.id where year = YEAR(getDate()) ");


			myRs=myStmt.executeQuery();
			while(myRs.next())
			{

				String name=myRs.getString("track_name");
				String genre=myRs.getString("genre");
				String artistName= myRs.getString("artist_name");
				String albumName= myRs.getString("album_name");
				int numPlays=myRs.getInt("num_plays");
				double duration = myRs.getDouble( "duration");
				System.out.println(name+" "+artistName+" "+albumName+" "+genre+" "+numPlays+" "+duration);
				tracks.add(new Track(name,genre,artistName,albumName,numPlays,duration));
			}
			System.out.println("GetAllTracks successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
		}

	}

	public ArrayList<Album> getAllAlbums(){					// to display all the latest albums on the homepage i.e., from this year

		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Album> albums=new ArrayList<Album>();
		try
		{
			myStmt=myConn.prepareStatement("SELECT * FROM albums where year = YEAR(getDate())");     //can use CURRENT_TIMESTAMP instead of getDate()
			myRs=myStmt.executeQuery();
			while(myRs.next())
			{

				String name=myRs.getString("album_name");
				int year=myRs.getInt("year");
				String genre=myRs.getString("genre");
				String imagePath=myRs.getString("image_path");
				float rating=myRs.getFloat("rating");
				System.out.println(name+" "+year+" "+genre+" "+imagePath+" "+rating);
				albums.add(new Album(name,year,genre,imagePath,rating));
			}
			System.out.println("GetAllTracks Query successful");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			return albums;
		}
	}

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
	
	
	public  ArrayList<Artist> searchArtists(String searchString) throws Exception
	{
		String s="%"+searchString+"%";
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Artist> artists=new ArrayList<Artist>();
		try
		{
			myStmt=myConn.prepareStatement("SELECT * FROM 	 artists where artist_name like ? ");
			myStmt.setString(1, s);
			
			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
			
				String name=myRs.getString("artist_name");
				String imagePath=myRs.getString("image_link");
				System.out.println(name+" "+imagePath);
				artists.add(new Artist(name,imagePath));
			}
			System.out.println("Query successful");
			return artists;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return artists;}
	}

	
	public  ArrayList<Album> searchAlbums(String searchString) throws Exception
	{
		String s="%"+searchString+"%";
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Album> albums=new ArrayList<Album>();
		try
		{
			myStmt=myConn.prepareStatement("SELECT * FROM 	 albums where album_name like ? ");
			myStmt.setString(1, s);
			
			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
			
				String name=myRs.getString("album_name");
				int year=myRs.getInt("year");
				String genre=myRs.getString("genre");
				String imagePath=myRs.getString("image_path");
				float rating=myRs.getFloat("rating");
				System.out.println(name+" "+year+" "+genre+" "+imagePath+" "+rating);
				albums.add(new Album(name,year,genre,imagePath,rating));
			}
			System.out.println("Query successful");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return albums;}
	}

	public ArrayList<Track> searchTracks(String searchString) throws Exception{
		String s="%"+searchString+"%";
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Track> tracks=new ArrayList<>();
		try
		{
			myStmt=myConn.prepareStatement("SELECT * FROM tracks where track_name like ? ");
			myStmt.setString(1, s);

			myRs=myStmt.executeQuery();
			while(myRs.next())
			{

				String name=myRs.getString("track_name");
				String genre=myRs.getString("genre");
				String artistName= myRs.getString("artist_name");
				String albumName= myRs.getString("album_name");
				int numPlays=myRs.getInt("num_plays");
				double duration = myRs.getDouble( "duration");



				System.out.println(name+" "+artistName+" "+albumName+" "+genre+" "+numPlays+" "+duration);
				tracks.add(new Track(name,genre,artistName,albumName,numPlays,duration));
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
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
