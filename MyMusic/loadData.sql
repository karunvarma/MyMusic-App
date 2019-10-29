# User
DELETE FROM User WHERE user_id >= 0;
ALTER TABLE User AUTO_INCREMENT = 1;
INSERT INTO User (name, username, password, admin) VALUES ("Michael", "admin", "admin", 1);
SELECT * FROM User;

# Artist
DELETE FROM Artist WHERE artist_id > 0;
ALTER TABLE Artist AUTO_INCREMENT = 1;
INSERT INTO Artist (name, imagePath, rating) VALUES ("Weird Al Yankovic", "MyMusic/Images/Artists/Weird_Al_Yankovic.jpg", 5.0);
INSERT INTO Artist (name, imagePath, rating) VALUES ("The Beatles", "MyMusic/Images/Artists/The_Beatles.jpeg", 5.0);
SELECT * FROM Artist;

# Album
DELETE FROM Album WHERE album_id > 0;
ALTER TABLE Album AUTO_INCREMENT = 1;
INSERT INTO Album (name, imagePath, genre, year, rating) VALUES ("Mandatory Fun", "MyMusic/Images/Albums/Mandatory_Fun.jpg", "Comedy", 2014, 5.0);
INSERT INTO Album (name, imagePath, genre, year, rating) VALUES ("Alpocalypse", "MyMusic/Images/Albums/Alpocalypse.jpg", "Comedy", 2011, 5.0);
INSERT INTO Album (name, imagePath, genre, year, rating) VALUES ("Abbey Road", "MyMusic/Images/Albums/Abbey_Road.jpg", "Rock", 1969, 5.0);
SELECT * FROM Album;

# Album_has_Artist
DELETE FROM Album_has_Artist WHERE album_id > 0;
ALTER TABLE Album AUTO_INCREMENT = 1;
INSERT INTO Album_has_Artist VALUES (1, 1);
INSERT INTO Album_has_Artist VALUES (2, 1);
INSERT INTO Album_has_Artist VALUES (3, 2);
SELECT * FROM Album_has_Artist;

# Track
DELETE FROM Track WHERE track_id > 0;
ALTER TABLE Track AUTO_INCREMENT = 1;
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Handy", "Comedy", 0,"00:02:56", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Lame Clain to Fame", "Comedy", 0,"00:03:45", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Foil", "Comedy", 0,"00:02:23", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Sports Song", "Comedy", 0,"00:02:15", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Word Crimes", "Comedy", 0,"00:03:43", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("My Own Eyes", "Comedy", 0,"00:03:41", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("NOW That's What I Call Polka!", "Comedy", 0,"00:04:06", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Mission Statement", "Comedy", 0,"00:04:29", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Inactive", "Comedy", 0,"00:02:56", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("First World Problems", "Comedy", 0,"00:03:14", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Tacky", "Comedy", 0,"00:02:53", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Jackson Park Express", "Comedy", 0,"00:09:05", 1);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Perform This Way", "Comedy", 0,"00:02:55", 2);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Polka Face", "Comedy", 0,"00:04:47", 2);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Party in the CIA", "Comedy", 0,"00:02:57", 2);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Come Together", "Rock", 0,"00:04:19", 3);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Here Comes the Sun", "Rock", 0,"00:03:06", 3);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Golden Slumbers", "Rock", 0,"00:01:32", 3);
INSERT INTO Track (name, genre, plays, time, album_id) VALUES ("Carry that Weight", "Rock", 0,"00:01:36", 3);
SELECT * FROM Track;

# Track_has_Artist
DELETE FROM Track_has_Artist WHERE artist_id > 0;
ALTER TABLE Track_has_Artist AUTO_INCREMENT = 1;
INSERT INTO Track_has_Artist VALUES (1, 1);
INSERT INTO Track_has_Artist VALUES (2, 1);
INSERT INTO Track_has_Artist VALUES (3, 1);
INSERT INTO Track_has_Artist VALUES (4, 1);
INSERT INTO Track_has_Artist VALUES (5, 1);
INSERT INTO Track_has_Artist VALUES (6, 1);
INSERT INTO Track_has_Artist VALUES (7, 1);
INSERT INTO Track_has_Artist VALUES (8, 1);
INSERT INTO Track_has_Artist VALUES (9, 1);
INSERT INTO Track_has_Artist VALUES (10, 1);
INSERT INTO Track_has_Artist VALUES (11, 1);
INSERT INTO Track_has_Artist VALUES (12, 1);
INSERT INTO Track_has_Artist VALUES (13, 1);
INSERT INTO Track_has_Artist VALUES (14, 1);
INSERT INTO Track_has_Artist VALUES (15, 1);
INSERT INTO Track_has_Artist VALUES (16, 2);
INSERT INTO Track_has_Artist VALUES (17, 2);
INSERT INTO Track_has_Artist VALUES (18, 2);
INSERT INTO Track_has_Artist VALUES (19, 2);
SELECT * FROM Track_has_Artist;

# Playlist
DELETE FROM Playlist WHERE playlist_id > 0;
ALTER TABLE Playlist AUTO_INCREMENT = 1;
INSERT INTO Playlist (name, user_id) VALUES ("Polka", 1);
SELECT * FROM Playlist;

# Playlist_has_Track
DELETE FROM Playlist_has_Track WHERE playlist_id > 0;
ALTER TABLE Playlist_has_Track AUTO_INCREMENT = 1;
INSERT INTO Playlist_has_Track VALUES (1, 7);
INSERT INTO Playlist_has_Track VALUES (1, 14);