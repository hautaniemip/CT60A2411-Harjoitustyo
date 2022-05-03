package com.tite.ct60a2411_harjoitustyo;

import static com.tite.ct60a2411_harjoitustyo.HelperFunctions.LoadImageFromUrl;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {
    private Button movie1Button;
    private Button movie2Button;
    private ImageButton imageButton;
    private View view;
    private Button popularButton;
    private ArrayList<Movie> movies;

    private SettingsManager settingsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsManager = SettingsManager.getInstance();
        view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = view.findViewById(R.id.firstImage);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable;
                drawable = LoadImageFromUrl("https://media.finnkino.fi/1012/Event_13317/landscape_large/FantasticBeasts3_670.jpg");
                MainActivity.getContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageDrawable(drawable);
                    }
                });
            }
        });
        thread.start();

        movie1Button = view.findViewById(R.id.movie1Button);
        movie2Button = view.findViewById(R.id.movie2Button);
        popularButton = view.findViewById(R.id.popularButton);
        //imageButton = view.findViewById(R.id.imageButton);

        String url = "https://www.finnkino.fi/xml/Schedule/?area=" + settingsManager.getHomeArea();

        XMLReaderTask reader = new XMLReaderTask(MainActivity.getContext(), url, "Show", MainActivity.getTags());
        reader.setShowDialog(false);
        reader.setCallback(this::dataCallback);
        reader.execute();

    }

    public void dataCallback(ArrayList<String[]> result) {
        if (result == null)
            return;

        movies = new ArrayList<>();

        for (String[] entry : result)
            movies.add(new Movie(entry));

        movie1Button.setText(movies.get(0).getTitle());
        movie1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(movies.get(0));
            }
        });

        movie2Button.setText(movies.get(1).getTitle());
        movie2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(movies.get(1));
            }
        });

        int popularIndex = new Random().nextInt(movies.size());
        popularButton.setText(movies.get(popularIndex).getTitle());
        popularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(movies.get(popularIndex));
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int movieIndex = 2;
                    int index = 1;
                    Drawable drawable;
                    while (movies.get(movieIndex) != null && index <= 10) {
                        String s = "imageButton" + index;

                        int resID = getResources().getIdentifier(s, "id", "com.tite.ct60a2411_harjoitustyo");
                        imageButton = view.findViewById(resID);
                        System.out.println(index + ":" + resID + " : " + imageButton);

                        drawable = LoadImageFromUrl(movies.get(movieIndex).getLargeImageUrl().replaceAll("^http://", "https://"));

                        final Drawable finalDrawable = drawable;
                        final int finalMovieIndex = movieIndex;
                        MainActivity.getContext().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalDrawable != null) {
                                    imageButton.setImageDrawable(finalDrawable);
                                }
                            }
                        });
                        // TODO: ImageButton functionality is here
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openActivity(movies.get(finalMovieIndex));
                            }
                        });
                        // TODO: and it ends here
                        index++;
                        movieIndex++;
                    }

                    System.out.println("Image loaded");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void openActivity(Movie movie) {
        Intent intent = new Intent(MainActivity.getContext(), MovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        intent.putExtras(bundle);
        MainActivity.getContext().startActivity(intent);
    }
}
