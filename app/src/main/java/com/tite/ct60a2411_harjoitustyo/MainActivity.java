package com.tite.ct60a2411_harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.security.cert.CertPathValidatorException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected static TheatreArea.AreaId areaId = TheatreArea.AreaId.STRAND;
    protected static ArrayList<TheatreArea> areas = new ArrayList<>();
    private String url = "https://www.finnkino.fi/xml/Schedule/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] tags = {"ID", "dttmShowStart", "dttmShowEnd", "Title", "OriginalTitle", "ProductionYear", "LengthInMinutes", "Rating", "TheatreID", "Theatre", "TheatreAuditorium"};

        for (TheatreArea.AreaId areaId : TheatreArea.AreaId.values()) {
            url = "https://www.finnkino.fi/xml/Schedule/?area=" + areaId.getId();
            XMLReaderTask reader = new XMLReaderTask(this, url, "Show", tags);
            reader.setCallback(MainActivity::dataCallback);
            reader.execute();
        }
    }

    public static void dataCallback(ArrayList<String[]> result) {
        TheatreArea area = new TheatreArea(areaId.getId());
        for (String[] entry : result) {
            Movie movie = new Movie(entry);
            area.addMovieToTheatre(movie.getTheatreId(), movie);
        }

        areas.add(area);
        area.printAreaInfo();
    }
}