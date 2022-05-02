package com.tite.ct60a2411_harjoitustyo;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieArchive implements Serializable {
    private static MovieArchive instance;
    private final ArrayList<Movie> movies = new ArrayList<>();

    private MovieArchive() {

    }

    public static MovieArchive getInstance() {
        if (instance == null) {
            instance = (MovieArchive) ObjectSaveUtils.readObject("MovieArchive.data");
            if (instance == null)
                instance = new MovieArchive();
        }

        return instance;
    }

    public void saveArchive() {
        ObjectSaveUtils.saveObject(instance, "MovieArchive.data");
    }

    public void addMovie(Movie movie) {
        // Removing unwanted attributes
        movie.setStartTime(null);
        movie.setEndTime(null);
        movie.setTheatreId(0);
        movie.setTheatreName(null);
        movie.setAuditorium(null);

        if (movies.isEmpty()) {
            movies.add(movie);
            return;
        }

        for (Movie movieIt : movies)
            if (movieIt.getEventId() == movie.getEventId())
                return;

        movies.add(movie);
    }

    public Movie getMovieByEventId(int eventId) {
        for (Movie movie : movies)
            if (movie.getEventId() == eventId)
                return movie;

        return null;
    }

    public void updateUserRating(int eventId, float rating) {
        for (Movie movie : movies) {
            if (movie.getEventId() == eventId) {
                movie.setUserRating(rating);
                return;
            }
        }
    }

    public void printArchiveInfo() {
        System.out.println("MovieArchive size: " + movies.size());
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
