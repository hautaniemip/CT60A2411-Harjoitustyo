package com.tite.ct60a2411_harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.security.cert.CertPathValidatorException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static TheatreArea.AreaId areaId = TheatreArea.AreaId.STRAND;
    private static ArrayList<TheatreArea> areas = new ArrayList<>();
    private static String[] tags = {"ID", "dttmShowStart", "dttmShowEnd", "Title", "OriginalTitle", "ProductionYear", "LengthInMinutes", "Rating", "TheatreID", "Theatre", "TheatreAuditorium"};
    private static String url;
    private static int areaIndex = 0;
    private static MainActivity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        readAllAreas();
    }

    // Read XML for every area sequentially
    public static void readAllAreas() {
        if (areaIndex == TheatreArea.AreaId.values().length)
            return;

        TheatreArea.AreaId areaId = TheatreArea.AreaId.values()[areaIndex];

        url = "https://www.finnkino.fi/xml/Schedule/?area=" + areaId.getId();
        XMLReaderTask reader = new XMLReaderTask(context, url, "Show", tags);
        reader.setCallback(MainActivity::dataCallback);
        reader.execute();

        areaIndex++;
    }

    // Callback function for XMLReader
    public static void dataCallback(ArrayList<String[]> result) {
        TheatreArea area = new TheatreArea(areaId.getId());
        for (String[] entry : result) {
            Movie movie = new Movie(entry);
            area.addMovieToTheatre(movie.getTheatreId(), movie);
        }

        areas.add(area);
        area.printAreaInfo();

        readAllAreas();
    }
}