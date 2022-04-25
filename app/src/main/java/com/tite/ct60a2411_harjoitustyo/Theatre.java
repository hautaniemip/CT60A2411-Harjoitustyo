package com.tite.ct60a2411_harjoitustyo;

import java.util.ArrayList;

public class Theatre {

    private final int theatreId;
    private final ArrayList<Movie> movies = new ArrayList<>();
    public Theatre(int theatreId) {
        this.theatreId = theatreId;
    }

    // Adds movie object to movies if it doesn't exist already
    public void addMovie(Movie movie) {
        if (!this.movies.contains(movie))
            this.movies.add(movie);
    }

    // Prints all movies in theatre and their info
    public void printTheatreInfo() {
        System.out.println("Theatre id: " + this.theatreId);
        System.out.println("Movies(" + movies.size() + "):");
        for (Movie movie : movies)
            movie.printMovieInfo();
    }

    public int getTheatreId() {
        return theatreId;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public enum TheatreId {
        OMENA(1056),
        SELLO(1050),
        ITIS(1058),
        KINOPALATSI_HELSINKI(1034),
        MAXIM(1047),
        TENNISPALATSI(1038),
        FLAMINGO(1043),
        FANTASIA(1044),
        SCALA(1049),
        KUVAPALATSI(1042),
        STRAND(1052),
        PLAZA(1036),
        PROMENADI(1039),
        CINE_ATLAS(1040),
        PLEVNA(1037),
        KINOPALATSI_TURKU(1035);

        private final int id;

        TheatreId(int id) {
            this.id = id;
        }
    }
}
