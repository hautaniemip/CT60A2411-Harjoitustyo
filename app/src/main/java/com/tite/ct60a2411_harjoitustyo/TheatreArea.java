package com.tite.ct60a2411_harjoitustyo;

import java.util.ArrayList;

public class TheatreArea {
    public enum AreaId {
        PAAKAUPUNKISEUTU (1014),
        ESPOO (1012),
        OMENA (1039),
        SELLO (1038),
        HELSINKI (1002),
        ITIS (1045),
        KINOPALATSI_HELSINKI (1031),
        MAXIM (1032),
        TENNISPALATSI (1033),
        FLAMINGO (1013),
        FANTASIA (1015),
        SCALA(1016),
        KUVAPALATSI (1017),
        STRAND (1041),
        PLAZA (1018),
        PROMENADI (1019),
        TAMPERE (1021),
        CINE_ATLAS (1034),
        PLEVNA (1035),
        KINOPALATSI_TURKU (1022);

        private int id;
        AreaId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private int areaId;
    private ArrayList<Theatre> theatres = new ArrayList<>();

    public TheatreArea(int areaId) {
        this.areaId = areaId;
    }

    public void addTheatre(Theatre theatre) {
        if (theatres.isEmpty()) {
            theatres.add(theatre);
            return;
        }

        for (Theatre theatreIt : this.theatres) {
            if (theatreIt.getTheatreId() != theatre.getTheatreId()) {
                theatres.add(theatre);
                return;
            }
        }

        theatres.add(theatre);
    }

    public Theatre getTheatreById(int theatreId) {
        if (theatres.isEmpty())
            return null;

        for (Theatre theatre : theatres)
            if (theatre.getTheatreId() == theatreId)
                return theatre;

        return null;
    }

    public void addMovieToTheatre(int theatreId, Movie movie) {
        Theatre theatre = this.getTheatreById(theatreId);
        if (theatre == null) {
            theatre = new Theatre(theatreId);
            theatre.addMovie(movie);
            this.addTheatre(theatre);
            return;
        }
        theatre.addMovie(movie);
    }

    public void printAreaInfo() {
        System.out.println("Theatre area id: " + this.areaId);
        System.out.println("Theatres (" + theatres.size() + "):");
        for (Theatre theatre : theatres)
            theatre.printTheatreInfo();
    }

    public int getAreaId() {
        return areaId;
    }

    public ArrayList<Theatre> getTheatres() {
        return theatres;
    }
}
