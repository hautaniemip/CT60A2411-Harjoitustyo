package com.tite.ct60a2411_harjoitustyo;

import static com.tite.ct60a2411_harjoitustyo.HelperFunctions.LoadImageFromUrl;
import static com.tite.ct60a2411_harjoitustyo.HelperFunctions.setFontSize;
import static com.tite.ct60a2411_harjoitustyo.HelperFunctions.setLanguage;

import android.database.MatrixCursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class MovieActivity extends AppCompatActivity {
    private SettingsManager settings;
    private MovieArchive archive;
    private Movie movie;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        archive = MovieArchive.getInstance();
        settings = SettingsManager.getInstance();

        Bundle bundle = getIntent().getExtras();

        // Check for movie
        if (bundle != null)
            movie = (Movie) bundle.getSerializable("movie");


        TextView errorText = findViewById(R.id.movieErrorText);
        TextView movieTitle = findViewById(R.id.movieTitle_activity);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        // Movie is given display its info
        if (movie != null) {
            SpannableString title = new SpannableString(movie.getTitle());
            title.setSpan(new RelativeSizeSpan(settings.getFontSize() + 1), 0, title.length(), 0);
            movieTitle.setText(title);

            Movie archivedMovie = archive.getMovieByEventId(movie.getEventId());

            if (archivedMovie != null)
                ratingBar.setRating(archivedMovie.getUserRating());

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    if (archivedMovie == null)
                        archive.addMovie(movie);

                    archive.updateUserRating(movie.getEventId(), ratingBar.getRating());
                }
            });

            int key = 0;
            final String[] matrix = {"_id", "key", "value"};
            final String[] columns = {"key", "value"};
            final int[] layouts = {R.id.key, R.id.value};
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            MatrixCursor cursor = new MatrixCursor(matrix);

            cursor.addRow(new Object[]{key++, getString(R.string.original_title), movie.getOriginalTitle()});
            cursor.addRow(new Object[]{key++, getString(R.string.rating), movie.getRating()});
            cursor.addRow(new Object[]{key++, getString(R.string.length), movie.getLength() + " min"});

            // Check if values exists, because MovieArchive doesn't store these
            if (movie.getStartTime() != null) {
                cursor.addRow(new Object[]{key++, getString(R.string.start_time), format.format(movie.getStartTime())});
                cursor.addRow(new Object[]{key++, getString(R.string.end_time), format.format(movie.getEndTime())});
            }

            cursor.addRow(new Object[]{key++, getString(R.string.year), movie.getYear()});

            // Check if values exists, because MovieArchive doesn't store these
            if (movie.getTheatreName() != null) {
                cursor.addRow(new Object[]{key++, getString(R.string.theatre), movie.getTheatreName() + ", " + movie.getAuditorium()});
            }

            SimpleCursorAdapter data = new SimpleCursorAdapter(this, R.layout.layout_two_list_item, cursor, columns, layouts);

            list = findViewById(R.id.dataContainer);
            list.setAdapter(data);

            ImageView imageView = findViewById(R.id.imageView);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Drawable drawable = LoadImageFromUrl(movie.getLargeImageUrl().replaceAll("^http://", "https://"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageDrawable(drawable);
                        }
                    });
                    System.out.println("Image loaded");
                }
            });
            thread.start();

        } else {
            errorText.setText(R.string.movie_error);
        }

        setSupportActionBar(findViewById(R.id.include));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setFontSize(this, settings.getFontSize());

        switch (settings.getLanguageIndex()) {
            case 0:
                setLanguage(this, "en");
                break;
            case 1:
                setLanguage(this, "fi");
                break;
            default:
                setLanguage(this, "en");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}