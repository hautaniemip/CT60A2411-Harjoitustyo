package com.tite.ct60a2411_harjoitustyo;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class MovieActivity extends AppCompatActivity {
    private Movie movie;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            movie = (Movie) bundle.getSerializable("movie");
        }

        TextView errorText = findViewById(R.id.movieErrorText);
        TextView movieTitle = findViewById(R.id.movieTitle_activity);

        if (movie != null) {
            movieTitle.setText(movie.getTitle());

            int key = 0;
            final String[] matrix = {"_id", "key", "value"};
            final String[] columns = {"key", "value"};
            final int[] layouts = {R.id.key, R.id.value};
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            MatrixCursor cursor = new MatrixCursor(matrix);

            cursor.addRow(new Object[]{key++, getString(R.string.original_title), movie.getOriginalTitle()});
            cursor.addRow(new Object[]{key++, getString(R.string.rating), movie.getRating()});
            cursor.addRow(new Object[]{key++, getString(R.string.length), movie.getLength() + " min"});

            if (movie.getStartTime() != null) {
                cursor.addRow(new Object[]{key++, getString(R.string.start_time), format.format(movie.getStartTime())});
                cursor.addRow(new Object[]{key++, getString(R.string.end_time), format.format(movie.getEndTime())});
            }

            cursor.addRow(new Object[]{key++, getString(R.string.year), movie.getYear()});
            if (movie.getTheatreName() != null) {
                cursor.addRow(new Object[]{key++, getString(R.string.theatre), movie.getTheatreName() + ", " + movie.getAuditorium()});
            }

            SimpleCursorAdapter data = new SimpleCursorAdapter(this, R.layout.layout_two_list_item, cursor, columns, layouts);

            list = findViewById(R.id.dataContainer);
            list.setAdapter(data);

        } else {
            errorText.setText("Error no movie");
        }

        setSupportActionBar(findViewById(R.id.include));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}