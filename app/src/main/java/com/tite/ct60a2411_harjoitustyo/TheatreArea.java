package com.tite.ct60a2411_harjoitustyo;

import java.util.ArrayList;

public class TheatreArea {
    public enum AreaId {
        OMENA (1039),
        SELLO (1038),
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
        CINE_ATLAS (1034),
        PLEVNA (1035),
        KINOPALATSI_TURKU (1022);

        private int id;
        AreaId(int id) {
            this.id = id;
        }
    }

    private int areaId;
    private ArrayList<Theatre> theatres;

    public TheatreArea(int areaId) {
        this.areaId = areaId;
    }

    public void addTheatre(Theatre theatre) {
        for (Theatre theatreIt : this.theatres) {
            if (theatreIt.getTheatreId() != theatre.getTheatreId())
                theatres.add(theatre);
        }
    }

    public void addMovieToTheatre(int theatreId, Movie movie) {
        for (Theatre theatre : theatres)
            if (theatre.getTheatreId() == theatreId)
                theatre.addMovie(movie);
    }

    public int getAreaId() {
        return areaId;
    }

    public ArrayList<Theatre> getTheatres() {
        return theatres;
    }
}
