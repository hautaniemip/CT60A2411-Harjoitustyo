package com.tite.ct60a2411_harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            System.out.println("Bundle NULL");
            movie = (Movie) bundle.getSerializable("movie");
        }

        TextView tempData = findViewById(R.id.tempMovieData);
        if (movie != null)
            tempData.setText(movie.toString());
        else
            tempData.setText("Error no movie");
    }
}