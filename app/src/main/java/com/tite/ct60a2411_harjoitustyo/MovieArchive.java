package com.tite.ct60a2411_harjoitustyo;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieArchive implements Serializable {
    private static MovieArchive instance;
    private final ArrayList<Movie> movies = new ArrayList<>();

    public MovieArchive() {

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

    // TODO: Get rid off unwanted attributes
    public void addMovie(Movie movie) {
        if (movies.isEmpty()) {
            movies.add(movie);
            return;
        }

        for (Movie movieIt : movies)
            if (movieIt.getEventId() == movie.getEventId())
                return;

        movies.add(movie);
    }

    public void printArchiveInfo() {
        System.out.println("MovieArchive size: " + movies.size());
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
