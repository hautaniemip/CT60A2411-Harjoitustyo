package com.tite.ct60a2411_harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String url = "https://www.finnkino.fi/xml/Schedule/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] tags = {"ID", "dttmShowStart", "dttmShowEnd", "Title", "OriginalTitle", "ProductionYear", "LengthInMinutes", "Rating", "Theatre", "TheatreAuditorium"};
        XMLReaderTask reader = new XMLReaderTask(this, url, "Show", tags);
        reader.setCallback(MainActivity::dataCallback);
        reader.execute();
    }

    public static void dataCallback(ArrayList<String[]> result) {
        System.out.println("###### TEST #####");
        System.out.println(new Movie(result.get(0)));
    }
}