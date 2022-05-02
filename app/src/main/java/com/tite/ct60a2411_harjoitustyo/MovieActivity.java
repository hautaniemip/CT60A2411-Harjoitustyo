package com.tite.ct60a2411_harjoitustyo;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MovieActivity extends AppCompatActivity {
    private SettingsManager settings;
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

            if (movie.getLargeImageUrl() != null)
                cursor.addRow(new Object[]{key++, "URL", movie.getLargeImageUrl()});

            SimpleCursorAdapter data = new SimpleCursorAdapter(this, R.layout.layout_two_list_item, cursor, columns, layouts);

            list = findViewById(R.id.dataContainer);
            list.setAdapter(data);

            ImageView imageView = (ImageView) findViewById(R.id.imageView);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Drawable d = LoadImageFromWebOperations(movie.getLargeImageUrl().replaceAll("^http://", "https://"));
                    imageView.setImageDrawable(d);
                    System.out.println("Image loaded");
                }
            });
            thread.start();

        } else {
            errorText.setText(R.string.movie_error);
        }

        setSupportActionBar(findViewById(R.id.include));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings = SettingsManager.getInstance();
        setFontSize(settings.getFontSize());

        switch (settings.getLanguageIndex()) {
            case 0:
                setLanguage("en");
                break;
            case 1:
                setLanguage("fi");
                break;
            default:
                setLanguage("en");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public void setFontSize(int size) {
        switch (size) {
            case 0:
                this.setTheme(R.style.FontSmall);
                break;
            case 1:
                this.setTheme(R.style.FontNormal);
                break;
            case 2:
                this.setTheme(R.style.FontLarge);
                break;
            default:
                this.setTheme(R.style.FontNormal);
        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            System.out.println("Loading the photo");
            return d;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}