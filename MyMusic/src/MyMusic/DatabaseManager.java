package MyMusic;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

	private Connection myConn;
	public DatabaseManager() throws Exception
	{
		String db_name="MyMusic";
		String dbUrl="jdbc:mysql://localhost:3306/"+db_name+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user="root";
		String password="root";
		
		myConn=DriverManager.getConnection(dbUrl,user,password);
		System.out.println("Database connected sucessfully");
	}


	// SEARCH METHODS //

	public  ArrayList<Artist> searchArtists(String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName, ArrayList<String> selectedGenres) throws Exception
	{
		String s="%"+searchString+"%";
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Artist> artists=new ArrayList<Artist>();
		try
		{
			myStmt = myConn.prepareStatement(
						"SELECT DISTINCT Artist.name, Artist.imagePath, Artist.rating FROM Artist" +
							" JOIN Album_has_Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" JOIN Album ON Album_has_Artist.album_id = Album.album_id" +
							" JOIN Track_has_Artist ON Track_has_Artist.artist_id = Artist.artist_id " +
							" JOIN Track ON Track.track_id = Track_has_Artist.track_id" +
								filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Track") +
								" ORDER BY Artist.name ASC;");;
			
			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
			
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				Float rating = myRs.getFloat("rating");
				System.out.println(name + " " + imagePath + " " + rating);
				artists.add(new Artist(name, imagePath, rating));
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
	
	public  ArrayList<Album> searchAlbums(String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName, ArrayList<String> selectedGenres) throws Exception
	{
		String s = "%"+searchString+"%";
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Album> albums = new ArrayList<Album>();
		try
		{
		    myStmt = myConn.prepareStatement(
		            	"SELECT DISTINCT Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name as artist_name FROM Album" +
                    		" JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" + " JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" JOIN Track ON Track.album_id = Album.album_id" +
							filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Album") +
							" ORDER BY Album.name ASC;");


			System.out.println(myStmt.toString());

			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				String name = myRs.getString("name");
                String imagePath = myRs.getString("imagePath");
                String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");
				System.out.println(name+" "+artistName+" "+year+" "+genre+" "+imagePath+" "+rating);
				albums.add(new Album(name, artistName, year, genre, imagePath, rating));
			}
			System.out.println("Query successful");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return albums;}
	}

	public ArrayList<Track> searchTracks(String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName, ArrayList<String> selectedGenres) throws Exception {
		String s = "%"+searchString+"%";
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.name, Track.genre, Track.plays, Track.time, Album.name as album_name, Artist.name as artist_name FROM Track" +
						" JOIN Album ON Album.album_id = Track.album_id" +
						" JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
						" JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
						filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Track") +
						" ORDER BY Album.name ASC, Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getTime( "time").toString();
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				System.out.println(name+" "+genre+" "+plays+" "+time+" "+artistName+" "+albumName);
				tracks.add(new Track(name, genre, plays, time, artistName, albumName));
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


	// GET METHODS //

	public User getUser(String username, String password) {
		try {
			// Login SQL Query
			PreparedStatement loginStmt = myConn.prepareStatement("Select * FROM User WHERE username = ?  AND password = ?");
			loginStmt.setString(1, username);
			loginStmt.setString(2, password);

			ResultSet rs = loginStmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String name = rs.getString("name");
                Boolean isAdmin = rs.getBoolean("admin");
				User user = new User(userId, name, username, password, isAdmin);
				user.setPlaylists(getPlaylists(userId));
				return user;
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
				//tracks.add(new Track(name,genre,artistName,albumName,numPlays,duration));
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
				String artistName=myRs.getString("artist_name");
				int year=myRs.getInt("year");
				String genre=myRs.getString("genre");
				String imagePath=myRs.getString("imagePath");
				float rating=myRs.getFloat("rating");
				System.out.println(name+" "+year+" "+genre+" "+imagePath+" "+rating);
				albums.add(new Album(name,artistName,year,genre,imagePath,rating));
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

	public ArrayList<Playlist> getPlaylists(int userId) {
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();
		PreparedStatement myStmt = null;
		ResultSet myRs;
		try
		{
			myStmt = myConn.prepareStatement("SELECT * From Playlist WHERE user_id = ?");
			myStmt.setInt(1, userId);



			myRs = myStmt.executeQuery();
			int index = 0;
			while(myRs.next())
			{
				int id = myRs.getInt("playlist_id");
				String name = myRs.getString("name");
				String imagePath = "MyMusic/fxml/musical-note.jpg";
				ArrayList<Track> tracks = getTracksInPlaylist(id);

				Playlist playlist = new Playlist(id, name, imagePath, userId, tracks);
				playlists.add(playlist);

				index++;
			}
			System.out.println("Query successful");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return playlists;}
	}

	public ArrayList<Track> getTracksInPlaylist(int playlistId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.name, Track.genre, Track.plays, Track.time, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" JOIN Album ON Album.album_id = Track.album_id" +
							" JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
							" JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" JOIN Playlist_has_Track on Track.track_id = Playlist_has_Track.track_id" +
							" WHERE Playlist_has_Track.playlist_id = ?" +
							" ORDER BY Album.name ASC, Track.name ASC;");
			myStmt.setInt(1, playlistId);
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getTime( "time").toString();
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				System.out.println(name+" "+genre+" "+plays+" "+time+" "+artistName+" "+albumName);
				tracks.add(new Track(name, genre, plays, time, artistName, albumName));
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


	// ADD METHODS //

	public void addTrack(Track track)
	{
		PreparedStatement myStmt=null;

		try
		{
			myStmt=myConn.prepareStatement("insert into tracks(track_name, album_id, time, num_plays) values (?,?,?,?)");
			myStmt.setString(1,track.getName());
			myStmt.setInt(2,track.getAlbumId());
			myStmt.setString(3,track.getTime());
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
			myStmt=myConn.prepareStatement("insert into albums (album_name,genre,year,imagePath,rating) values (?,?,?,?,?)");
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

	public boolean addPlaylist(Playlist playlist) {
		Boolean success = false;
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("INSERT INTO Playlist (name, user_id) VALUES (?, ?);");
			myStmt.setString(1, playlist.getName());
			myStmt.setInt(2, playlist.getUserId());
			myStmt.executeUpdate();
			success = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean addTracksToPlaylist(Playlist playlist, ArrayList<Track> tracks) {
		Boolean success = true;
		for (int i = 0; i < tracks.size(); i++) {
			PreparedStatement myStmt = null;
			try {
				myStmt = myConn.prepareStatement("INSERT INTO Playlist_has_Track VALUES (?, ?);");
				myStmt.setInt(1, playlist.getId());
				myStmt.setInt(2, tracks.get(i).getId());
				myStmt.executeUpdate();
			}
			catch (Exception e) {
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}

	// SQL GENERATION METHODS //
	private String filterSQL(String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName) {
		if (!searchByTrackName && !searchByAlbumName && !searchByArtistName) {
			return " WHERE true";
		}
		else {
			searchString = "\'%"+searchString+"%\'";
			String sql = "";
			sql = " WHERE (";
			if (searchByTrackName) {
				sql += " Track.name LIKE " + searchString;
			}
			if (searchByAlbumName) {
				if (searchByTrackName) {
					sql += " OR";
				}
				sql += " Album.name LIKE " + searchString;
			}
			if (searchByArtistName) {
				if (searchByTrackName || searchByAlbumName) {
					sql += " OR";
				}
				sql += " Artist.name LIKE " + searchString;
			}
			sql += ")";
			return sql;
		}
	}

	private String genreSQL(ArrayList<String> selectedGenres, String tableName) {
	    String sql = "";
	    if (selectedGenres.size() > 0) {
	        sql += " AND (";
        }
        else {
        	return "";
		}

        for (int i = 0; i < selectedGenres.size(); i++) {
            sql += tableName + ".genre = \'" + selectedGenres.get(i) + "\'";
            if (i != selectedGenres.size() - 1) {
                sql += " OR ";
            }
            else {
                sql += ")";
            }
        }
        return sql;
    }
}
