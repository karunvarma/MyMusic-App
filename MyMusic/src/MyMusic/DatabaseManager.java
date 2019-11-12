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
		
		myConn=DriverManager.getConnection(dbUrl, user, password);
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
						"SELECT DISTINCT Artist.artist_id, Artist.name, Artist.imagePath, Artist.rating FROM Artist" +
							" JOIN Album_has_Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" JOIN Album ON Album_has_Artist.album_id = Album.album_id" +
							" JOIN Track_has_Artist ON Track_has_Artist.artist_id = Artist.artist_id " +
							" JOIN Track ON Track.track_id = Track_has_Artist.track_id" +
								filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Track") +
								" ORDER BY Artist.name ASC;");;
			
			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("artist_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				Float rating = myRs.getFloat("rating");

				Artist artist = new Artist(id, name, imagePath, rating);
				artist.setAlbums(getAlbumsByArtist(id));
				artist.setTracks(getTracksByArtist(id));
				artists.add(artist);
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
		            	"SELECT DISTINCT Album.album_id, Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name as artist_name FROM Album" +
                    		" JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
							" JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" JOIN Album_has_Track ON Album_has_Track.album_id = Album.album_id" +
							" JOIN Track ON Track.track_id = Album_has_Track.track_id" +
							filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Album") +
							" ORDER BY Album.name ASC;");


			System.out.println(myStmt.toString());

			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("album_id");
				String name = myRs.getString("name");
                String imagePath = myRs.getString("imagePath");
                String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");
				System.out.println(name+" "+artistName+" "+year+" "+genre+" "+imagePath+" "+rating);
				Album album = new Album(id, name, artistName, year, genre, imagePath, rating);
				album.setTracks(getTracksInAlbum(id));
				albums.add(album);
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
						" JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
						" JOIN Album ON Album.album_id = Album_has_Track.album_id" +
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
				String time = myRs.getString( "time");
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

	public boolean isUsernameTaken(String username) {
		try {
			// Login SQL Query
			PreparedStatement loginStmt = myConn.prepareStatement("Select username FROM User WHERE username = ?");
			loginStmt.setString(1, username);

			ResultSet rs = loginStmt.executeQuery();

			if (rs.next()) {
				return true;
			}
			else {
				// Result set is empty. Therefore, a user with the given username and password does not exist in the database.
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public ArrayList<Track> getAllTracks() {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Track.mediaPath, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
							" JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" ORDER BY Album.name ASC, Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time").toString();
				String mediaPath = myRs.getString("mediaPath");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				tracks.add(new Track(id, name, genre, plays, time, artistName, albumName, mediaPath));
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

	public ArrayList<Album> getAllAlbums() {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Album> albums = new ArrayList<Album>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Album.album_id, Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name as artist_name FROM Album" +
							" JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" + " JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" JOIN Album_has_Track ON Album_has_Track.album_id = Album.album_id" +
							" JOIN Track ON Track.track_id = Album_has_Track.track_id" +
							" ORDER BY Album.name ASC;");
			System.out.println(myStmt.toString());

			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("album_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");

				Album album = new Album(id, name, artistName, year, genre, imagePath, rating);
				album.setTracks(getTracksInAlbum(id));
				albums.add(album);
			}
			System.out.println("Query successful");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return albums;}
	}

	public ArrayList<Artist> getAllArtists() {
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Artist> artists=new ArrayList<Artist>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Artist.artist_id, Artist.name, Artist.imagePath, Artist.rating FROM Artist" +
							" JOIN Album_has_Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" JOIN Album ON Album_has_Artist.album_id = Album.album_id" +
							" JOIN Track_has_Artist ON Track_has_Artist.artist_id = Artist.artist_id " +
							" JOIN Track ON Track.track_id = Track_has_Artist.track_id" +
							" ORDER BY Artist.name ASC;");

			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("artist_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				Float rating = myRs.getFloat("rating");

				Artist artist = new Artist(id, name, imagePath, rating);
				artist.setAlbums(getAlbumsByArtist(id));
				artist.setTracks(getTracksByArtist(id));
				artists.add(artist);
			}
			System.out.println("Query successful");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return artists;}
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
							" JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" JOIN Track_has_Artist ON Track.track_id = Track_has_Artist.track_id" +
							" JOIN Artist ON Track_has_Artist.artist_id = Artist.artist_id" +
							" JOIN Playlist_has_Track on Track.track_id = Playlist_has_Track.track_id" +
							" WHERE Playlist_has_Track.playlist_id = " + playlistId +
							" ORDER BY Album.name ASC, Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");
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

	public ArrayList<Track> getTracksInAlbum(int albumId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<Track>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" JOIN Track_has_Artist ON Track.track_id = Track_has_Artist.track_id" +
							" JOIN Artist ON Track_has_Artist.artist_id = Artist.artist_id" +
							" WHERE Album_has_Track.album_id = " + albumId +
							" ORDER BY Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

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

	public ArrayList<Album> getAlbumsByArtist(int artistId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Album> albums = new ArrayList<Album>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Album.album_id, Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name AS artist_name FROM Album" +
							" JOIN Album_has_Artist ON Album_has_Artist.album_id = Album.album_id" +
							" JOIN Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" WHERE Artist.artist_id = " + artistId +
							" ORDER BY Album.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("album_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");
				System.out.println(name+" "+artistName+" "+year+" "+genre+" "+imagePath+" "+rating);
				Album album = new Album(id, name, artistName, year, genre, imagePath, rating);
				album.setTracks(getTracksInAlbum(id));
				albums.add(album);

				//albums.add(new Track(name, genre, plays, time, artistName, albumName));
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return albums;
		}
	}

	public ArrayList<Track> getTracksByArtist(int artistId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<Track>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" JOIN Track_has_Artist ON Track.track_id = Track_has_Artist.track_id" +
							" JOIN Artist ON Track_has_Artist.artist_id = Artist.artist_id" +
							" WHERE Track_has_Artist.artist_id = " + artistId +
							" ORDER BY Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

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

	// SAVE & EXISTS METHODS //

	public void saveTrack(Track track) {

	}

	public void saveAlbum(Album album) {

	}

	public void saveArtist(Artist artist) {

	}

	public void savePlaylist(Playlist playlist) {
		PreparedStatement myStmt = null;
		try {
			if (playlistExists(playlist)) {
				updatePlaylist(playlist);
			}
			else {
				addPlaylist(playlist);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean trackExists(Track track) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM Track WHERE track_id = ?");
			myStmt.setInt(1, track.getId());
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.getFetchSize() == 1) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	public boolean albumExists(Album album) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM Album WHERE album_id = ?");
			myStmt.setInt(1, album.getId());
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.getFetchSize() == 1) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	public boolean artistExists(Artist artist) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM Track WHERE track_id = ?");
			myStmt.setInt(1, artist.getId());
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.getFetchSize() == 1) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	public boolean playlistExists(Playlist playlist) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM Playlist WHERE playlist_id = ?");
			myStmt.setInt(1, playlist.getId());
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.getFetchSize() == 1) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	// ADD & UPDATE METHODS //

	public boolean addUser(User user) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO USER(name, username, password, admin) values (?,?,?,?)");
			myStmt.setString(1, user.getName());
			myStmt.setString(2, user.getUsername());
			myStmt.setString(3, user.getPassword());
			myStmt.setBoolean(4, user.isAdmin());
			myStmt.executeUpdate();
			System.out.println("user added successfully");
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}

	public void addTrack(Track track)
	{
		PreparedStatement myStmt = null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Track(name, genre, plays, time, mediaPath) VALUES (?, ?, ?, ?, ?)");
			myStmt.setString(1, track.getName());
			myStmt.setString(2, track.getGenre());
			myStmt.setInt(3, track.getNumPlays());
			myStmt.setString(4, track.getTime());
			myStmt.setString(5, track.getMediaPath());
			myStmt.executeUpdate();
			System.out.println("Track added successfully");
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
			myStmt = myConn.prepareStatement("INSERT INTO Artist (name, imagePath, rating) values (?, ?, ?)");
			myStmt.setString(1, artist.getName());
			myStmt.setString(2, artist.getImagePath());
			myStmt.setFloat(3, artist.getRating());
			myStmt.executeUpdate();
			System.out.println("Artist added successfully");
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
			myStmt = myConn.prepareStatement("INSERT INTO Album (name, imagePath, genre, year, rating) values (?,?,?,?,?)");
			myStmt.setString(1, album.getName());
			myStmt.setString(2, album.getImagePath());
			myStmt.setString(3, album.getGenre());
			myStmt.setInt(4, album.getYear());
			myStmt.setFloat(5, album.getRating());
			myStmt.executeUpdate();
			System.out.println("Album added successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addAlbum_has_Artist(Album album, Artist artist) {

	}

	public void addAlbum_has_Track(Album album, Track track) {

	}

	public void addTrack_has_Artist(Track track, Artist artist) {

	}


	public void updateTrack(Track track) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Track SET name = ?, genre = ?, plays = ?, time = ?, mediaPath = ? WHERE track_id = ?;");
			myStmt.setString(1, track.getName());
			myStmt.setString(2, track.getGenre());
			myStmt.setInt(3, track.getNumPlays());
			myStmt.setString(4, track.getTime());
			myStmt.setString(5, track.getMediaPath());
			myStmt.setInt(6, track.getId());
			myStmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTrackPlays(Track track) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Track SET plays = ? WHERE track_id = ?");
			myStmt.setInt(1, track.getNumPlays());
			myStmt.setInt(2, track.getId());
			myStmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateArtist(Artist artist) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Artist SET name = ?, imagePath = ?, rating = ? WHERE artist_id = ?");
			myStmt.setString(1, artist.getName());
			myStmt.setString(2, artist.getImagePath());
			myStmt.setFloat(3, artist.getRating());
			myStmt.setInt(4, artist.getId());
			myStmt.executeUpdate();
			//updatePlaylistHasTrack(album);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateAlbum(Album album) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Album SET name = ?, imagePath = ?, genre = ?, year = ?, rating = ? WHERE album_id = ?");
			myStmt.setString(1, album.getName());
			myStmt.setString(2, album.getImagePath());
			myStmt.setString(3, album.getGenre());
			myStmt.setInt(4, album.getYear());
			myStmt.setFloat(5, album.getRating());
			myStmt.setInt(6, album.getId());
			myStmt.executeUpdate();
			//updatePlaylistHasTrack(album);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePlaylist(Playlist playlist) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Playlist SET name = ? WHERE playist_id = ?");
			myStmt.setString(1, playlist.getName());
			myStmt.setInt(2, playlist.getId());
			myStmt.executeUpdate();
			updatePlaylistHasTrack(playlist);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePlaylistHasTrack(Playlist playlist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM Playlist_has_Track WHERE playlist_id = " + playlist.getId() + ";";
			String addSql = addPlaylist_hasTrackSQL(playlist);
			myStmt.addBatch(deleteSQL);
			myStmt.addBatch(addSql);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPlaylist(Playlist playlist) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("INSERT INTO Playlist (name, user_id) VALUES (?, ?);");
			myStmt.setString(1, playlist.getName());
			myStmt.setInt(2, playlist.getUserId());
			myStmt.executeUpdate();
			addTracksToPlaylist(playlist, playlist.getTracks());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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

	// DELETE METHODS //

	public void deleteArtist(Artist artist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteArtistSQL = "DELETE FROM Artist WHERE artist_id = " + artist.getId() + ";";
			String deleteTrackHasArtistSQL = "DELETE FROM Track_has_Artist WHERE artist_id = " + artist.getId() + ";";
			String deleteAlbumHasArtistSQL = "DELETE FROM Album_has_Artist WHERE artist_id = " + artist.getId() + ";";
			myStmt.addBatch(deleteArtistSQL);
			myStmt.addBatch(deleteTrackHasArtistSQL);
			myStmt.addBatch(deleteAlbumHasArtistSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAlbum(Album album) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteAlbumSQL = "DELETE FROM Album WHERE album_id = " + album.getId() + ";";
			String deleteAlbumHasTrackSQL = "DELETE FROM Album_has_Track WHERE album_id = " + album.getId() + ";";
			String deleteAlbumHasArtistSQL = "DELETE FROM Album_has_Artist WHERE album_id = " + album.getId() + ";";
			myStmt.addBatch(deleteAlbumSQL);
			myStmt.addBatch(deleteAlbumHasTrackSQL);
			myStmt.addBatch(deleteAlbumHasArtistSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteTrack(Track track) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteTrackSQL = "DELETE FROM Track WHERE track_id = " + track.getId() + ";";
			String deleteAlbumHasTrackSQL = "DELETE FROM Album_has_Track WHERE track_id = " + track.getId() + ";";
			String deleteTrackHasArtistSQL = "DELETE FROM Track_ha-s_Artist WHERE track_id = " + track.getId() + ";";
			myStmt.addBatch(deleteTrackSQL);
			myStmt.addBatch(deleteAlbumHasTrackSQL);
			myStmt.addBatch(deleteTrackHasArtistSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deletePlaylist(Playlist playlist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deletePlaylistSQL = "DELETE FROM Playlist WHERE playlist_id = " + playlist.getId() + ";";
			String deletePlaylistHasTrackSQL = "DELETE FROM Playlist_has_Track WHERE playlist_id = " + playlist.getId() + ";";
			myStmt.addBatch(deletePlaylistSQL);
			myStmt.addBatch(deletePlaylistHasTrackSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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

    private String addPlaylist_hasTrackSQL(Playlist playlist) {
		String sql = "";
		for (int i = 0; i < playlist.getTracks().size(); i++) {
			sql += "INSERT INTO Playlist_has_Track Values (" + playlist.getId() + ", " + playlist.getTracks().get(i).getId() + "); ";
		}

		return sql;
	}
}
